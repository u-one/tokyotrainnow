package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import net.uoneweb.tokyotrainnow.odpt.entity.Train;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TestDataBuilder {

    public static Operator jrEast() {
        return Operator.builder()
                .id("urn:ucode:_00001C000000000000010000030E6606")
                .date(LocalDateTime.of(2019, 04, 22, 15, 00, 00))
                .title("JR東日本")
                .sameAs("odpt.Operator:JR-East")
                .operatorTitles(Map.of("en", "JR East", "ja", "JR東日本"))
                .build();
    }
    public static Operator tokyoTrans() {
        return Operator.builder()
                .id("urn:ucode:_00001C000000000000010000030E6609")
                .date(LocalDateTime.of(2019, 04, 22, 15, 00, 00))
                .title("東京都交通局")
                .sameAs("odpt.Operator:Toei")
                .operatorTitles(Map.of("en", "Tokyo Metropolitan Bureau of Transportation", "ja", "東京都交通局"))
                .build();
    }

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

    public static Station uchiboKimitsu() {
        return Station.builder()
                .title("君津")
                .sameAs("odpt.Station:JR-East.Uchibo.Kimitsu")
                .operator("odpt.Operator:JR-East")
                .railway("odpt.Railway:JR-East.Uchibo")
                .stationTitle(Map.of("en", "Kimitsu","ja", "君津"))
                .geo_lat(35.33356)
                .geo_long(139.89538)
                .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Kimitsu"))
                .stationTimetables(List.of(
                        "odpt.StationTimetable:JR-East.SobuRapid.Inage.Inbound.Weekday",
                        "odpt.StationTimetable:JR-East.SobuRapid.Inage.Inbound.SaturdayHoliday",
                        "odpt.StationTimetable:JR-East.SobuRapid.Inage.Outbound.Weekday",
                        "odpt.StationTimetable:JR-East.SobuRapid.Inage.Outbound.SaturdayHoliday"
                        )
                )
                .build();
    }

    public static Station yokosukaOfuna() {
        return Station.builder()
                .title("大船")
                .sameAs("odpt.Station:JR-East.Yokosuka.Ofuna")
                .operator("odpt.Operator:JR-East")
                .railway("odpt.Railway:JR-East.Yokosuka")
                .stationCode("JO09")
                .stationTitle(Map.of("en", "Ofuna", "ja", "大船"))
                .geo_lat(35.35383)
                .geo_long(139.53124)
                .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Ofuna"))
                .stationTimetables(List.of(
                                "odpt.StationTimetable:JR-East.Yokosuka.Ofuna.Inbound.Weekday",
                                "odpt.StationTimetable:JR-East.Yokosuka.Ofuna.Inbound.SaturdayHoliday",
                                "odpt.StationTimetable:JR-East.Yokosuka.Ofuna.Outbound.Weekday",
                                "odpt.StationTimetable:JR-East.Yokosuka.Ofuna.Outbound.SaturdayHoliday"
                        )
                )
                .connectingRailways(List.of(
                                "odpt.Railway:JR-East.KeihinTohokuNegishi",
                                "odpt.Railway:JR-East.ShonanShinjuku",
                                "odpt.Railway:JR-East.Tokaido"
                        )
                )
                .build();

    }

    public static Railway soubuRapid() {
        return Railway.builder()
                .id("urn:ucode:_00001C00000000000001000003100E1D")
                .sameAs("odpt.Railway:JR-East.SobuRapid")
                .date(LocalDateTime.of(2021,06,21,14,00,00))
                .title("総武快速線")
                .color("#0074BE")
                .lineCode("JO")
                .operator("odpt.Operator:JR-East")
                .railwayTitles(Map.of("en", "Sobu Rapid Line","ja", "総武快速線"))
                .stationOrder(List.of(
                        Railway.Station.builder()
                                .index(1)
                                .station("odpt.Station:JR-East.SobuRapid.Tokyo")
                                .stationTitles(Map.of("en", "Tokyo","ja", "東京"))
                                .build(),
                        Railway.Station.builder()
                                .index(2)
                                .station("odpt.Station:JR-East.SobuRapid.ShinNihombashi")
                                .stationTitles(Map.of("en", "Shin-Nihombashi","ja", "新日本橋"))
                                .build(),
                        Railway.Station.builder()
                                .index(3)
                                .station("odpt.Station:JR-East.SobuRapid.Bakurocho")
                                .stationTitles(Map.of("en", "Bakurocho","ja", "馬喰町"))
                                .build(),
                        Railway.Station.builder()
                                .index(4)
                                .station("odpt.Station:JR-East.SobuRapid.Kinshicho")
                                .stationTitles(Map.of("en", "Kinshicho","ja", "錦糸町"))
                                .build(),
                        Railway.Station.builder()
                                .index(5)
                                .station("odpt.Station:JR-East.SobuRapid.ShinKoiwa")
                                .stationTitles(Map.of("en", "Shin-Koiwa","ja", "新小岩"))
                                .build(),
                        Railway.Station.builder()
                                .index(6)
                                .station("odpt.Station:JR-East.SobuRapid.Ichikawa")
                                .stationTitles(Map.of("en", "Ichikawa","ja", "市川"))
                                .build(),
                        Railway.Station.builder()
                                .index(7)
                                .station("odpt.Station:JR-East.SobuRapid.Funabashi")
                                .stationTitles(Map.of("en", "Funabashi","ja", "船橋"))
                                .build(),
                        Railway.Station.builder()
                                .index(8)
                                .station("odpt.Station:JR-East.SobuRapid.Tsudanuma")
                                .stationTitles(Map.of("en", "Tsudanuma","ja", "津田沼"))
                                .build(),
                        Railway.Station.builder()
                                .index(9)
                                .station("odpt.Station:JR-East.SobuRapid.Inage")
                                .stationTitles(Map.of("en", "Inage","ja", "稲毛"))
                                .build(),
                        Railway.Station.builder()
                                .index(10)
                                .station("odpt.Station:JR-East.SobuRapid.Chiba")
                                .stationTitles(Map.of("en", "Chiba","ja", "千葉"))
                                .build()
                ))
                .ascendingRailDirection("odpt.RailDirection:Outbound")
                .descendingRailDirection("odpt.RailDirection:Inbound")
                .build();
    }

    public static Train train2296FAtTokyo() {
        return Train.builder()
                .id("urn:uuid:0557289e-7209-458f-b8b6-eac9f37e99e4")
                .date(LocalDateTime.of(2021,10,1, 12,0,0))
                .valid(LocalDateTime.of(2021,10,1, 12,5,0))
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
                .build();
    }

    public static Train train575FOnLineInageToChiba() {
        return Train.builder()
                .id("urn:uuid:f5e7a725-33a8-4b4c-a89e-8ba2aba9d6dc")
                .date(LocalDateTime.of(2021,10,1, 12,0,0))
                .valid(LocalDateTime.of(2021,10,1, 12,5,0))
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
                .build();
    }
}
