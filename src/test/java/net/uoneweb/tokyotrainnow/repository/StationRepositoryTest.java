package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;
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
public class StationRepositoryTest {
    @Autowired
    private StationRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();

        Station shinjuku = Station.builder()
                .title("新宿")
                .sameAs("odpt.Station:JR-East.ChuoRapid.Shinjuku")
                .operator("odpt.Operator:JR-East")
                .railway("odpt.Railway:JR-East.ChuoRapid")
                .stationCode("JC05")
                .stationTitle(Map.of("en", "Shinjuku","ja", "新宿"))
                .geo_lat(35.69018)
                .geo_long(139.70038)
                .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Shinjuku"))
                .stationTimetables(List.of(
                        "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Inbound.Weekday",
                        "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Inbound.SaturdayHoliday",
                        "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Outbound.Weekday",
                        "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Outbound.SaturdayHoliday"))
                .connectingRailways(List.of(
                        "odpt.Railway:JR-East.ChuoSobuLocal",
                        "odpt.Railway:JR-East.SaikyoKawagoe",
                        "odpt.Railway:JR-East.ShonanShinjuku",
                        "odpt.Railway:JR-East.Yamanote",
                        "odpt.Railway:Keio.Keio",
                        "odpt.Railway:Keio.KeioNew",
                        "odpt.Railway:Odakyu.Odawara",
                        "odpt.Railway:Seibu.Shinjuku",
                        "odpt.Railway:Toei.Oedo",
                        "odpt.Railway:Toei.Shinjuku",
                        "odpt.Railway:TokyoMetro.Marunouchi"))
                .build();

        Station seijo = Station.builder()
                .id("urn:ucode:_00001C000000000000010000031022A3")
                .title("成城学園前")
                .sameAs("odpt.Station:Odakyu.Odawara.SeijogakuenMae")
                .operator("odpt.Operator:Odakyu")
                .railway("odpt.Railway:Odakyu.Odawara")
                .stationTitle(Map.of("en", "Seijogakuen-Mae","ja", "成城学園前"))
                .build();

        repository.add("odpt.Station:JR-East.ChuoRapid.Shinjuku", shinjuku);
        repository.add("odpt.Station:Odakyu.Odawara.SeijogakuenMae", seijo);
    }

    @Test
    public void findAllSuccess() {
        List<Station> stations = repository.findAll();
        List<String> titles = stations.stream().map(o -> o.getTitle()).sorted().collect(Collectors.toList());
        assertIterableEquals(List.of("成城学園前", "新宿"), titles);
    }

    @Test
    public void findByStationIdSuccess() {
        Station station = repository.findByStationId("odpt.Station:JR-East.ChuoRapid.Shinjuku");
        assertEquals("新宿", station.getTitle());
    }
}
