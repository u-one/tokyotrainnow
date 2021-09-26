package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;

import java.util.List;
import java.util.Map;

public class TestDataBuilder {

    public static Station sobuRapidTokyo() {
        return Station.builder()
                .title("東京")
                .sameAs("odpt.Station:JR-East.SobuRapid.Tokyo")
                .operator("odpt.Operator:JR-East")
                .railway("odpt.Railway:JR-East.SobuRapid")
                .stationCode("JO19")
                .stationTitle(Map.of("en", "Tokyo","ja", "東京"))
                .geo_lat(35.6813)
                .geo_long(139.76704)
                .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Tokyo"))
                .stationTimetables(List.of(
                        "odpt.StationTimetable:JR-East.SobuRapid.Tokyo.Outbound.Weekday",
                        "odpt.StationTimetable:JR-East.SobuRapid.Tokyo.Outbound.SaturdayHoliday"))
                .connectingRailways(List.of(
                        "odpt.Railway:JR-East.AkitaShinkansen",
                        "odpt.Railway:JR-East.ChuoRapid",
                        "odpt.Railway:JR-East.HokurikuShinkansen",
                        "odpt.Railway:JR-East.JobanRapid",
                        "odpt.Railway:JR-East.JoetsuShinkansen",
                        "odpt.Railway:JR-East.KeihinTohokuNegishi",
                        "odpt.Railway:JR-East.Keiyo",
                        "odpt.Railway:JR-East.Takasaki",
                        "odpt.Railway:JR-East.TohokuShinkansen",
                        "odpt.Railway:JR-East.Tokaido",
                        "odpt.Railway:JR-East.Utsunomiya",
                        "odpt.Railway:JR-East.YamagataShinkansen",
                        "odpt.Railway:JR-East.Yamanote",
                        "odpt.Railway:JR-East.Yokosuka",
                        "odpt.Railway:TokyoMetro.Marunouchi"))
                .build();
    }

    public static Station sobuRapidInage() {
        return Station.builder()
                .title("稲毛")
                .sameAs("odpt.Station:JR-East.SobuRapid.Inage")
                .operator("odpt.Operator:JR-East")
                .railway("odpt.Railway:JR-East.SobuRapid")
                .stationCode("JO27")
                .stationTitle(Map.of("en", "Inage","ja", "稲毛"))
                .geo_lat(35.63714)
                .geo_long(140.09257)
                .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Inage"))
                .stationTimetables(List.of(
                                "odpt.StationTimetable:JR-East.SobuRapid.Inage.Inbound.Weekday",
                                "odpt.StationTimetable:JR-East.SobuRapid.Inage.Inbound.SaturdayHoliday",
                                "odpt.StationTimetable:JR-East.SobuRapid.Inage.Outbound.Weekday",
                                "odpt.StationTimetable:JR-East.SobuRapid.Inage.Outbound.SaturdayHoliday"
                        )
                )
                .connectingRailways(List.of(
                                "odpt.Railway:JR-East.ChuoSobuLocal"
                        )
                )
                .build();
    }

    public static Station sobuRapidChiba() {
        return Station.builder()
                .title("千葉")
                .sameAs("odpt.Station:JR-East.SobuRapid.Chiba")
                .operator("odpt.Operator:JR-East")
                .railway("odpt.Railway:JR-East.SobuRapid")
                .stationCode("JO28")
                .stationTitle(Map.of("en", "Chiba","ja", "千葉"))
                .geo_lat(35.61353)
                .geo_long(140.11257)
                .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Chiba"))
                .stationTimetables(List.of(
                                "odpt.StationTimetable:JR-East.SobuRapid.Inage.Inbound.Weekday",
                                "odpt.StationTimetable:JR-East.SobuRapid.Inage.Inbound.SaturdayHoliday"
                        )
                )
                .connectingRailways(List.of(
                        "odpt.Railway:JR-East.ChuoSobuLocal",
                        "odpt.Railway:JR-East.Sobu",
                        "odpt.Railway:JR-East.Sotobo",
                        "odpt.Railway:JR-East.Uchibo",
                        "odpt.Railway:Keisei.Chiba"
                        )
                )
                .build();
    }


}
