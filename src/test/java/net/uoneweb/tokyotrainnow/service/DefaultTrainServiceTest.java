package net.uoneweb.tokyotrainnow.service;

import net.uoneweb.tokyotrainnow.RailDirectionFactory;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.TrainTypeFactory;
import net.uoneweb.tokyotrainnow.controller.TrainOnRail;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.entity.MetaData;
import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import net.uoneweb.tokyotrainnow.odpt.entity.Train;
import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;
import net.uoneweb.tokyotrainnow.repository.MetaDataRepository;
import net.uoneweb.tokyotrainnow.repository.OperatorRepository;
import net.uoneweb.tokyotrainnow.repository.RailDirectionRepository;
import net.uoneweb.tokyotrainnow.repository.RailwayRepository;
import net.uoneweb.tokyotrainnow.repository.StationRepository;
import net.uoneweb.tokyotrainnow.repository.TrainRepository;
import net.uoneweb.tokyotrainnow.repository.TrainTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultTrainServiceTest {

    private AutoCloseable closable;

    @Mock
    private Clock clock;

    @Mock
    private MetaDataRepository metaDataRepository;

    @Mock
    private OdptApiClient odptApiClient;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private RailDirectionRepository railDirectionRepository;

    @Mock
    private RailwayRepository railwayRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private TrainTypeRepository trainTypeRepository;

    @InjectMocks
    DefaultTrainService trainService;

    @BeforeEach
    void setup() {
        closable = MockitoAnnotations.openMocks(this);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    public void updateSuccess() {
        when(clock.instant()).thenReturn(Instant.parse("2021-10-01T12:00:00.000Z"));
        when(clock.withZone(ZoneId.of("Asia/Tokyo"))).thenReturn(clock);

        final Operator jrEast = TestDataBuilder.jrEast();
        when(odptApiClient.getOperators()).thenReturn(List.of(jrEast));
        when(operatorRepository.save(jrEast)).thenReturn(null);

        final RailDirection inbound = RailDirectionFactory.inbound();
        when(odptApiClient.getRailDirections()).thenReturn(List.of(inbound));
        when(railDirectionRepository.save(inbound)).thenReturn(null);

        final Railway sobuRapid = TestDataBuilder.soubuRapid();
        when(odptApiClient.getRailways()).thenReturn(List.of(sobuRapid));
        when(railwayRepository.save(sobuRapid)).thenReturn(null);

        final Station tokyo = TestDataBuilder.sobuRapidTokyo();
        when(odptApiClient.getStations()).thenReturn(List.of(tokyo));
        when(stationRepository.save(tokyo)).thenReturn(null);

        final TrainType rapid = TrainTypeFactory.rapid();
        when(odptApiClient.getTrainTypes()).thenReturn(List.of(rapid));
        when(trainTypeRepository.save(rapid)).thenReturn(null);

        LocalDateTime expectedTime = LocalDateTime.of(2021,10,1,12,00,00);
        final MetaData metaData = MetaData.builder()
                .operatorsUpdateTime(expectedTime)
                .railwaysUpdateTime(expectedTime)
                .railDirectionsUpdateTime(expectedTime)
                .stationsUpdateTime(expectedTime)
                .trainTypesUpdateTime(expectedTime)
                .build();
        when(metaDataRepository.save(metaData)).thenReturn(null);

        trainService.update();

        verify(operatorRepository, times(1)).save(jrEast);
        verify(railDirectionRepository, times(1)).save(inbound);
        verify(railwayRepository, times(1)).save(sobuRapid);
        verify(stationRepository, times(1)).save(tokyo);
        verify(trainTypeRepository, times(1)).save(rapid);
        verify(metaDataRepository, times(1)).save(metaData);
    }

    @Test
    public void getRailwaySuccess() {
        when(railwayRepository.findById("odpt.Railway:JR-East.SobuRapid")).thenReturn(Optional.of(createRailway()));
        Railway railway = trainService.getRailway("odpt.Railway:JR-East.SobuRapid");
        assertEquals("総武快速線", railway.getTitle());
    }

    @Test
    public void getCurrentRailwaySuccess() {
        when(clock.instant()).thenReturn(Instant.parse("2021-10-01T12:00:00.000Z"));
        when(clock.withZone(ZoneId.of("Asia/Tokyo"))).thenReturn(clock);
        when(railwayRepository.findById("odpt.Railway:JR-East.SobuRapid")).thenReturn(Optional.of(createRailway()));
        when(railDirectionRepository.findById("odpt.RailDirection:Inbound")).thenReturn(Optional.of(RailDirection.builder()
                .railDirectionTitles(Map.of("en", "Inbound", "ja", "上り"))
                .build()));
        when(railDirectionRepository.findById("odpt.RailDirection:Outbound")).thenReturn(Optional.of(RailDirection.builder()
                .railDirectionTitles(Map.of("en", "Outbound", "ja", "下り"))
                .build()));
        when(operatorRepository.findById("odpt.Operator:JR-East")).thenReturn(Optional.of(Operator.builder()
                .operatorTitles(Map.of("en", "JR East", "ja", "JR東日本"))
                .build()));
        when(stationRepository.findById("odpt.Station:JR-East.SobuRapid.Tokyo")).thenReturn(Optional.of(TestDataBuilder.sobuRapidTokyo()));
        when(stationRepository.findById("odpt.Station:JR-East.SobuRapid.Inage")).thenReturn(Optional.of(TestDataBuilder.sobuRapidInage()));
        when(stationRepository.findById("odpt.Station:JR-East.SobuRapid.Chiba")).thenReturn(Optional.of(TestDataBuilder.sobuRapidChiba()));
        when(stationRepository.findById("odpt.Station:JR-East.Yokosuka.Ofuna")).thenReturn(Optional.of(TestDataBuilder.yokosukaOfuna()));
        when(stationRepository.findById("odpt.Station:JR-East.Uchibo.Kimitsu")).thenReturn(Optional.of(TestDataBuilder.uchiboKimitsu()));
        when(trainRepository.find("odpt.Railway:JR-East.SobuRapid")).thenReturn(List.of(
                TestDataBuilder.train2296FAtTokyo(),
                TestDataBuilder.train575FOnLineInageToChiba()
        ));
        when(trainTypeRepository.findById("odpt.TrainType:JR-East.Rapid")).thenReturn(Optional.of(TrainType.builder()
                .sameAs("odpt.TrainType:JR-East.Rapid")
                .title("快速")
                .operator("odpt.Operator:JR-East")
                .trainTypeTitles(Map.of("en", "Rapid","ja", "快速"))
                .build()));

        LocalDateTime updateTime = LocalDateTime.of(2021,10,01,12,00,00);
        when(metaDataRepository.findById(1L)).thenReturn(Optional.of(MetaData.builder()
                        .operatorsUpdateTime(updateTime)
                        .railwaysUpdateTime(updateTime)
                        .railDirectionsUpdateTime(updateTime)
                        .stationsUpdateTime(updateTime)
                        .trainTypesUpdateTime(updateTime)
                .build()));

        CurrentRailway railway = trainService.getCurrentRailway("odpt.Railway:JR-East.SobuRapid");
        assertThat(railway.getTitle()).isEqualTo("総武快速線");

        /*
        Sections
        0: Station 東京, 上り:2296F
        1: Line 東京-稲毛
        2: Station 稲毛
        3: Line 稲毛-千葉 下り: 575F
        4: Station 千葉
        */

        assertThat(railway.getSections()).hasSize(5);
        assertThat(railway.getSections())
                .elements(0, 2, 4).hasOnlyElementsOfType(CurrentRailway.EStation.class);
        assertThat(railway.getSections())
                .elements(1,3).hasOnlyElementsOfType(CurrentRailway.Line.class);

        // 東京
        assertThat(railway.getSections().get(0).getTrains())
                .extracting(TrainOnRail::getTrainNumber).containsExactly("2296F");

        // 千葉-稲毛
        assertThat(railway.getSections().get(3).getTrains())
                .extracting(TrainOnRail::getTrainNumber).containsExactly("575F");

        assertThat(railway.getOperatorUpdateTime()).isEqualTo(updateTime);
        assertThat(railway.getRailwayUpdateTime()).isEqualTo(updateTime);
        assertThat(railway.getTrainTypeUpdateTime()).isEqualTo(updateTime);
        assertThat(railway.getTrainDate()).isEqualTo(LocalDateTime.of(2021,10,1, 12,0,0));
        assertThat(railway.getValidSeconds()).isEqualTo(300);
    }

    @Test
    public void findRailway_NotFound_ExceptionThrown() {
        assertThatThrownBy(() -> {
            trainService.findRailway("unknown");
        });
    }

    @Test
    public void findRailDirection_NotFound_ExceptionThrown() {
        assertThatThrownBy(() -> {
            trainService.findRailDirection("unknown");
        });
    }

    @Test
    public void findOperator_NotFound_ExceptionThrown() {
        assertThatThrownBy(() -> {
            trainService.findOperator("unknown");
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void findStation_EmptyId_ReturnsEmpty(String stationId) {
        final Station actual = trainService.findStation(stationId);
        assertThat(actual).isEqualTo(Station.EMPTY);
    }

    @Test
    public void findStation_NotFound_ExceptionThrown() {
        assertThatThrownBy(() -> {
            trainService.findStation("unknown");
        });
    }

    @Test
    public void findTrainType_NotFound_ExceptionThrown() {
        assertThatThrownBy(() -> {
            trainService.findTrainType("unknown");
        });
    }


    @ParameterizedTest
    @CsvSource({
            "odpt.RailDirection:Outbound, odpt.RailDirection:Inbound, odpt.RailDirection:Outbound, true",
            "odpt.RailDirection:Outbound, odpt.RailDirection:Inbound, odpt.RailDirection:Inbound, false",
            "odpt.RailDirection:Outbound, odpt.RailDirection:Inbound, other, false",
    })
    public void isAscendingDriectionTest(String railwayAscDir, String railwayDscDir, String trainDir, boolean expected) {
        Train train = Train.builder().railDirection(trainDir).build();
        Railway railway = Railway.builder()
                .ascendingRailDirection(railwayAscDir)
                .descendingRailDirection(railwayDscDir)
                                .build();

        assertThat(trainService.isAscendingDirection(train, railway)).isEqualTo(expected);
    }


    private static Railway createRailway() {
        Railway sobuRapid = Railway.builder()
                .id("urn:ucode:_00001C00000000000001000003100E1D")
                .sameAs("odpt.Railway:JR-East.SobuRapid")
                .title("総武快速線").color("#0074BE").lineCode("JO")
                .operator("odpt.Operator:JR-East")
                .railwayTitles(Map.of("en", "Sobu Rapid Line","ja", "総武快速線"))
                .stationOrder(List.of(Railway.Station.builder()
                        .index(1).station("odpt.Station:JR-East.SobuRapid.Tokyo")
                        .stationTitles(Map.of("en", "Tokyo","ja", "東京"))
                        .build(),
                        Railway.Station.builder()
                                .index(2).station("odpt.Station:JR-East.SobuRapid.Inage")
                                .stationTitles(Map.of("en", "Inage","ja", "稲毛"))
                                .build(),
                        Railway.Station.builder()
                                .index(3).station("odpt.Station:JR-East.SobuRapid.Chiba")
                                .stationTitles(Map.of("en", "Chiba","ja", "千葉"))
                                .build()
                        ))
                .ascendingRailDirection("odpt.RailDirection:Outbound")
                .descendingRailDirection("odpt.RailDirection:Inbound")
                .build();
        return sobuRapid;
    }
}
