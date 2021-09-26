package net.uoneweb.tokyotrainnow.service;

import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.repository.RailwayRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DefaultTrainServiceTest {

    private AutoCloseable closable;

    @Mock
    private OdptApiClient odptApiClient;

    @Mock
    private RailwayRepository railwayRepository;

    @InjectMocks
    DefaultTrainService trainService;

    @BeforeEach
    void setup() {
        closable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    public void updateSuccess() {
        Railway railway = createRailway();
        when(odptApiClient.getRailways()).thenReturn(List.of(railway));
        doNothing().when(railwayRepository).add("odpt.Railway:JR-East.SobuRapid", railway);

        trainService.update();
    }

    @Test
    public void getRailwaySuccess() {
        when(railwayRepository.findByRailwayId("odpt.Railway:JR-East.SobuRapid")).thenReturn(createRailway());
        Railway railway = trainService.getRailway("odpt.Railway:JR-East.SobuRapid");
        assertEquals("総武快速線", railway.getTitle());
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
                        .build()))
                .ascendingRailDirection("odpt.RailDirection:Outbound")
                .descendingRailDirection("odpt.RailDirection:Inbound")
                .build();
        return sobuRapid;
    }
}
