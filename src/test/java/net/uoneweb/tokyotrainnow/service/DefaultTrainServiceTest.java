package net.uoneweb.tokyotrainnow.service;

import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.*;
import net.uoneweb.tokyotrainnow.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    private DefaultRailDirectionRepository railDirectionRepository;

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
        Railway railway = createRailway();
        when(clock.instant()).thenReturn(Instant.parse("2021-10-01T12:00:00.000Z"));
        when(clock.withZone(ZoneId.of("Asia/Tokyo"))).thenReturn(clock);
        when(odptApiClient.getRailways()).thenReturn(List.of(railway));
        when(railwayRepository.save(railway)).thenReturn(null);

        trainService.update();

        LocalDateTime expectedTime = LocalDateTime.of(2021,10,1,12,00,00);
        verify(metaDataRepository, times(1)).setOperatorsUpdateTime(expectedTime);
        verify(metaDataRepository, times(1)).setRailwaysUpdateTime(expectedTime);
        verify(metaDataRepository, times(1)).setRailDirectionsUpdateTime(expectedTime);
        verify(metaDataRepository, times(1)).setStationsUpdateTime(expectedTime);
        verify(metaDataRepository, times(1)).setTrainTypesUpdateTime(expectedTime);
    }

    @Test
    public void getRailwaySuccess() {
        when(railwayRepository.findById("odpt.Railway:JR-East.SobuRapid")).thenReturn(Optional.of(createRailway()));
        Railway railway = trainService.getRailway("odpt.Railway:JR-East.SobuRapid");
        assertEquals("総武快速線", railway.getTitle());
    }

    @Test
    public void getCurrentRailwaySuccess() {
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
        when(stationRepository.findByStationId("odpt.Station:JR-East.SobuRapid.Tokyo")).thenReturn(TestDataBuilder.sobuRapidTokyo());
        when(stationRepository.findByStationId("odpt.Station:JR-East.SobuRapid.Inage")).thenReturn(TestDataBuilder.sobuRapidInage());
        when(stationRepository.findByStationId("odpt.Station:JR-East.SobuRapid.Chiba")).thenReturn(TestDataBuilder.sobuRapidChiba());
        when(stationRepository.findByStationId("odpt.Station:JR-East.Yokosuka.Ofuna")).thenReturn(TestDataBuilder.yokosukaOfuna());
        when(stationRepository.findByStationId("odpt.Station:JR-East.Uchibo.Kimitsu")).thenReturn(TestDataBuilder.uchiboKimitsu());
        when(trainRepository.find("odpt.Railway:JR-East.SobuRapid")).thenReturn(createTrains());
        when(trainTypeRepository.findByTrainTypeId("odpt.TrainType:JR-East.Rapid")).thenReturn(TrainType.builder()
                .sameAs("odpt.TrainType:JR-East.Rapid")
                .title("快速")
                .operator("odpt.Operator:JR-East")
                .trainTypeTitles(Map.of("en", "Rapid","ja", "快速"))
                .build());

        LocalDateTime updateTime = LocalDateTime.of(2021,10,01,12,00,00);
        when(metaDataRepository.getOperatorsUpdateTime()).thenReturn(updateTime);
        when(metaDataRepository.getRailwaysUpdateTime()).thenReturn(updateTime);
        when(metaDataRepository.getTrainTypesUpdateTime()).thenReturn(updateTime);

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
                .elements(0, 2, 4).hasOnlyElementsOfType(CurrentRailway.Station.class);
        assertThat(railway.getSections())
                .elements(1,3).hasOnlyElementsOfType(CurrentRailway.Line.class);

        // 東京
        assertThat(railway.getSections().get(0).getTrains())
                .extracting(CurrentRailway.Train::getTrainNumber).containsExactly("2296F");

        // 千葉-稲毛
        assertThat(railway.getSections().get(3).getTrains())
                .extracting(CurrentRailway.Train::getTrainNumber).containsExactly("575F");

        assertThat(railway.getOperatorUpdateTime()).isEqualTo(updateTime);
        assertThat(railway.getRailwayUpdateTime()).isEqualTo(updateTime);
        assertThat(railway.getTrainTypeUpdateTime()).isEqualTo(updateTime);
        assertThat(railway.getTrainDate()).isEqualTo(LocalDateTime.of(2021,9,5, 5,11,15));
    }

    @Test
    public void lastTrainDateReturnsLatestOne() {
        List<Train> trains = List.of(
                Train.builder().date(LocalDateTime.of(2021,10,01,12,00,00)).build(),
                Train.builder().date(LocalDateTime.of(2021,10,01,12,00,01)).build(),
                Train.builder().date(LocalDateTime.of(2021,10,01,12,00,02)).build()
                );
        assertThat(trainService.lastTrainDate(trains)).isEqualTo(LocalDateTime.of(2021, 10,01, 12,00,02));
    }

    @Test
    public void lastTrainDateMayReturnNull() {
        List<Train> trains = List.of();
        assertThat(trainService.lastTrainDate(trains)).isNull();
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

    @ParameterizedTest
    @CsvSource({
            "odpt.Station:JR-East.SobuRapid.Tokyo, , true, 0",
            "odpt.Station:JR-East.SobuRapid.Tokyo, odpt.Station:JR-East.SobuRapid.Inage, true, 1",
            "odpt.Station:JR-East.SobuRapid.Inage, , true, 2",
            "odpt.Station:JR-East.SobuRapid.Chiba, , true, 4",
            "odpt.Station:JR-East.SobuRapid.Chiba, , false, 4",
            "odpt.Station:JR-East.SobuRapid.Chiba, odpt.Station:JR-East.SobuRapid.Inage , false, 3",
            "odpt.Station:JR-East.SobuRapid.Tokyo, , false, 0",
    })
    public void findIndexTest(String from, String to, boolean ascending, int expected) {
        List<CurrentRailway.Section> sections = List.of(
                CurrentRailway.Station.builder().stationId("odpt.Station:JR-East.SobuRapid.Tokyo").build(),
                CurrentRailway.Line.builder().build(),
                CurrentRailway.Station.builder().stationId("odpt.Station:JR-East.SobuRapid.Inage").build(),
                CurrentRailway.Line.builder().build(),
                CurrentRailway.Station.builder().stationId("odpt.Station:JR-East.SobuRapid.Chiba").build()
        );
        int index = trainService.findIndex(sections, from, to, ascending);

        assertThat(index).isEqualTo(expected);
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


    private static List<Train> createTrains() {
        return List.of(
                Train.builder()
                        .id("urn:uuid:0557289e-7209-458f-b8b6-eac9f37e99e4")
                        .date(LocalDateTime.of(2021,9,5, 5,11,15))
                        .valid(LocalDateTime.of(2021,9,5, 5,16,15))
                        .sameAs("odpt.Train:JR-East.SobuRapid.2296F")
                        .railway("odpt.Railway:JR-East.SobuRapid")
                        .operator("odpt.Operator:JR-East")
                        .trainType("odpt.TrainType:JR-East.Rapid")
                        .trainNumber("2296F")
                        .carComposition(11)
                        .fromStation("odpt.Station:JR-East.SobuRapid.Tokyo")
                        .toStation(null)
                        .railDirection("odpt.RailDirection:Inbound")
                        .destinationStations(List.of("odpt.Station:JR-East.Yokosuka.Ofuna"))
                        .delay(0)
                        .build(),
                Train.builder()
                        .id("urn:uuid:f5e7a725-33a8-4b4c-a89e-8ba2aba9d6dc")
                        .date(LocalDateTime.of(2021,9,5, 5,11,15))
                        .valid(LocalDateTime.of(2021,9,5, 5,16,15))
                        .sameAs("odpt.Train:JR-East.SobuRapid.575F")
                        .railway("odpt.Railway:JR-East.SobuRapid")
                        .operator("odpt.Operator:JR-East")
                        .trainType("odpt.TrainType:JR-East.Rapid")
                        .trainNumber("575F")
                        .carComposition(15)
                        .fromStation("odpt.Station:JR-East.SobuRapid.Inage")
                        .toStation("odpt.Station:JR-East.SobuRapid.Chiba")
                        .railDirection("odpt.RailDirection:Outbound")
                        .destinationStations(List.of("odpt.Station:JR-East.Uchibo.Kimitsu"))
                        .delay(0)
                        .build()
        );
    }
}
