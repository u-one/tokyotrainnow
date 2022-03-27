package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.entity.CurrentRailway;

import java.time.LocalDateTime;
import java.util.List;

public class CurrentRailwayBuilder {
    public static CurrentRailway subuRapid() {
        return CurrentRailway.builder()
                .title("総武快速線")
                .lineCode("JO")
                .color("#0074BE")
                .operator("odpt.Operator:JR-East")
                .sections(List.of(tokyo(List.of(t2115f())), line(List.of()), inage(List.of()), line(List.of()), chiba(List.of())))
                .ascendingTitle("上り")
                .descendingTitle("下り")
                .operatorUpdateTime(LocalDateTime.of(2021, 3, 27, 12, 0, 0))
                .railwayUpdateTime(LocalDateTime.of(2021, 3, 27, 12, 0, 1))
                .trainTypeUpdateTime(LocalDateTime.of(2021, 3, 27, 12, 0, 2))
                .trainDate(LocalDateTime.of(2021, 3, 27, 12, 0, 3))
                .validSeconds(90)
                .build();
    }

    static CurrentRailway.Station tokyo(List<CurrentRailway.Train> trains) {
        return createStation("東京", "odpt.Station:JR-East.SobuRapid.Tokyo", "JO19", trains);
    }

    static CurrentRailway.Station inage(List<CurrentRailway.Train> trains) {
        return createStation("稲毛", "odpt.Station:JR-East.SobuRapid.Inage", "JO27", trains);
    }

    static CurrentRailway.Station chiba(List<CurrentRailway.Train> trains) {
        return createStation("千葉", "odpt.Station:JR-East.SobuRapid.Chiba", "JO28", trains);
    }

    static CurrentRailway.Station createStation(String title, String stationId, String stationCode, List<CurrentRailway.Train> trains) {
        CurrentRailway.Station station = new CurrentRailway.Station();
        for ( CurrentRailway.Train train: trains) {
            station.addTrain(train);
        }
        return station;
    }

    static CurrentRailway.Line line(List<CurrentRailway.Train> trains) {
        CurrentRailway.Line line = new CurrentRailway.Line("|");
        for ( CurrentRailway.Train train: trains) {
            line.addTrain(train);
        }
        return line;
    }

    static CurrentRailway.Train t2115f() {
        return new CurrentRailway.Train("千葉","2115F","快速",15,0, false);
    }

}
