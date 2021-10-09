package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.Train;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TrainRepositoryTest {
    @Mock
    private OdptApiClient odptApiClient;

    @InjectMocks
    TrainRepository repository;

    private AutoCloseable closable;

    @BeforeEach
    void setup() {
        closable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    public void findByStationIdSuccess() {
        when(odptApiClient.getTrain("odpt.Railway:JR-East.SobuRapid"))
                .thenReturn(
                        List.of(
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
                                        .trainNumber("2296F")
                                        .carComposition(15)
                                        .fromStation("odpt.Station:JR-East.SobuRapid.Inage")
                                        .toStation("odpt.Station:JR-East.SobuRapid.Chiba")
                                        .railDirection("odpt.RailDirection:Outbound")
                                        .destinationStations(List.of("odpt.Station:JR-East.Uchibo.Kimitsu"))
                                        .delay(0)
                                        .build()
                        )
                );

        List<Train> trains = repository.find("odpt.Railway:JR-East.SobuRapid");

        assertThat(trains).hasSize(2);
    }
}
