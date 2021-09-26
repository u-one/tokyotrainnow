package net.uoneweb.tokyotrainnow.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentRailway {

    private String title;

    private String lineCode;

    private String color;

    private String operator;

    private List<Section> sections;

    // 列車の在線位置をあらわす区間単位
    @Data
    public static abstract class Section {
        private String title = "";

        private String stationId = "";

        private String stationCode = "";

        private List<Train> trains = new ArrayList<>();

        public void addTrain(Train train) {
            trains.add(train);
        }
    }

    // 駅をあらわす
    @NoArgsConstructor
    // @Dataは使えない
    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class Station extends Section {
        private net.uoneweb.tokyotrainnow.odpt.entity.Station odptStation;

        @Builder
        public Station(String title, String stationId, String stationCode, net.uoneweb.tokyotrainnow.odpt.entity.Station odptStation) {
            this.setTitle(title);
            this.setStationId(stationId);
            this.setStationCode(stationCode);
            this.odptStation = odptStation;
        }
    }

    // 駅間の走行区間をあらわす
    @NoArgsConstructor
    // @Dataは使えない
    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class Line extends Section {
        @Builder
        public Line(String title) {
            this.setTitle(title);
        }
    }

    @Data
    @Builder
    public static class Train {
        @NonNull
        String destination;

        @NonNull
        String trainNumber;

        @NonNull
        String trainType;

        int carComposition;

        int delay;
    }
}