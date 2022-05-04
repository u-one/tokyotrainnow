package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.controller.TrainOnRail;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;

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

    public static CurrentRailway.EStation tokyo(List<TrainOnRail> trains) {
        return createStation("東京", "odpt.Station:JR-East.SobuRapid.Tokyo", "JO19", trains);
    }

    public static CurrentRailway.EStation inage(List<TrainOnRail> trains) {
        return createStation("稲毛", "odpt.Station:JR-East.SobuRapid.Inage", "JO27", trains);
    }

    static CurrentRailway.EStation chiba(List<TrainOnRail> trains) {
        return createStation("千葉", "odpt.Station:JR-East.SobuRapid.Chiba", "JO28", trains);
    }

    static CurrentRailway.EStation createStation(String title, String stationId, String stationCode, List<TrainOnRail> trains) {
        CurrentRailway.EStation station = CurrentRailway.EStation.builder()
                .title(title)
                .stationId(stationId)
                .stationCode(stationCode)
                .build();

        for ( TrainOnRail train: trains) {
            station.addTrain(train);
        }
        return station;
    }

    public static CurrentRailway.Line line(List<TrainOnRail> trains) {
        CurrentRailway.Line line = new CurrentRailway.Line("|");
        for ( TrainOnRail train: trains) {
            line.addTrain(train);
        }
        return line;
    }

    public static TrainOnRail t2115f() {
        return new TrainOnRail("2115F", TrainTypeFactory.rapid(),15,0, false, TestDataBuilder.sobuRapidTokyo(), Station.EMPTY ,List.of(), "ja");
    }

}
