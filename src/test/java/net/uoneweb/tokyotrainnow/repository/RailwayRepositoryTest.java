package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RailwayRepositoryTest {
    @Autowired
    private RailwayRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();

        Railway sobu = Railway.builder()
                .id("urn:ucode:_00001C00000000000001000003100E21")
                .sameAs("odpt.Railway:JR-East.ChuoSobuLocal")
                .title("中央・総武各駅停車").color("#FFE500").lineCode("JB")
                .operator("odpt.Operator:JR-East")
                .railwayTitles(Map.of("en", "Chuo Sobu Local Line","ja", "中央・総武各駅停車"))
                .stationOrder(List.of(Railway.Station.builder()
                        .index(1).station("odpt.Station:JR-East.ChuoSobuLocal.Chiba")
                        .stationTitles(Map.of("en", "Chiba","ja", "千葉"))
                        .build()))
                .ascendingRailDirection("odpt.RailDirection:Westbound")
                .descendingRailDirection("odpt.RailDirection:Eastbound")
                .build();

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

        Railway odakyuOdawara = Railway.builder()
                .id("urn:ucode:_00001C00000000000001000003100E52")
                .sameAs("odpt.Railway:Odakyu.Odawara")
                .title("小田原線")
                .operator("odpt.Operator:Odakyu")
                .railwayTitles(Map.of("en", "Odawara Line","ja", "小田原線"))
                .stationOrder(List.of())
                .ascendingRailDirection("odpt.RailDirection:Outbound")
                .descendingRailDirection("odpt.RailDirection:Inbound")
                .build();

        repository.add("odpt.Railway:JR-East.ChuoSobuLocal",  sobu);
        repository.add("odpt.Railway:JR-East.SobuRapid",  sobuRapid);
        repository.add("odpt.Railway:Odakyu.Odawara",  odakyuOdawara);
    }

    @Test
    public void findAllSuccess() {
        List<Railway> railways = repository.findAll();
        List<String> titles = railways.stream().map(o -> o.getTitle()).sorted().collect(Collectors.toList());
        assertIterableEquals(List.of("中央・総武各駅停車", "小田原線", "総武快速線"), titles);
    }

    @Test
    public void findByOperatorIdSuccess() {
        List<Railway> railways = repository.findByOperatorId("odpt.Operator:JR-East");
        List<String> titles = railways.stream().map(o -> o.getTitle()).sorted().collect(Collectors.toList());
        assertIterableEquals(List.of("中央・総武各駅停車", "総武快速線"), titles);
    }

    @Test
    public void findByRailwayIdSuccess() {
        Railway railway = repository.findByRailwayId("odpt.Railway:JR-East.SobuRapid");
        assertEquals("総武快速線", railway.getTitle());
    }
}
