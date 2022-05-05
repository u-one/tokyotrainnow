package net.uoneweb.tokyotrainnow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.uoneweb.tokyotrainnow.controller.Sections;
import net.uoneweb.tokyotrainnow.controller.TrainOnRail;
import net.uoneweb.tokyotrainnow.controller.Trains;
import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class CurrentRailway {

    private String title;

    private String lineCode;

    private String color;

    private String operator;

    private List<Section> sections;

    private String ascendingTitle;

    private String descendingTitle;

    private LocalDateTime operatorUpdateTime;

    private LocalDateTime railwayUpdateTime;

    private LocalDateTime trainTypeUpdateTime;

    private LocalDateTime trainDate;

    private long validSeconds;

    public CurrentRailway(final Operator operator, final Railway railway, final RailDirection ascending, final RailDirection descending,
                          final List<Station> stations, final Trains trains, final MetaData metaData, final Clock clock, String lang) {

        this.title = railway.getTitle();
        this.lineCode = railway.getLineCode();
        this.color = railway.getColor();
        this.operator = operator.getOperatorTitles().get(lang);
        this.ascendingTitle = ascending.getRailDirectionTitles().get(lang);
        this.descendingTitle = descending.getRailDirectionTitles().get(lang);

        Sections sections = new Sections(lang);
        for (Station station : stations) {
            sections = sections.add(station);
        }
        for (TrainOnRail train : trains.asList()) {
            sections.add(train);
        }
        this.sections = sections.asList();

        initializeDate(metaData, trains, clock);
    }

    void initializeDate(final MetaData metadata, final Trains trains, final Clock clock) {
        this.operatorUpdateTime = metadata.getOperatorsUpdateTime();
        this.railwayUpdateTime = metadata.getRailwaysUpdateTime();
        this.trainTypeUpdateTime = metadata.getRailwaysUpdateTime();
        this.trainDate = trains.lastTrainDate();
        this.validSeconds = calculateValidLimit(trains, clock);
    }

    static long calculateValidLimit(final Trains trains, final Clock clock) {
        final LocalDateTime now = LocalDateTime.now(clock.withZone(ZoneId.of("Asia/Tokyo")));
        LocalDateTime limit = trains.validLimit();
        if (limit == null) {
            limit = now.plusMinutes(5);
        }
        return ChronoUnit.SECONDS.between(now, limit);
    }

    // 列車の在線位置をあらわす区間単位
    @Data
    public static abstract class Section {
        private String title = "";

        private String stationId = "";

        private String stationCode = "";

        private List<TrainOnRail> trains = new ArrayList<>();

        public void addTrain(TrainOnRail train) {
            trains.add(train);
        }

        public List<TrainOnRail> getTrainsByDirection(boolean ascending) {
            return trains.stream().filter(t -> t.isAscending() == ascending)
                    .collect(Collectors.toList());
        }
    }

    // 駅をあらわす
    @NoArgsConstructor
    // @Dataは使えない
    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class EStation extends Section {
        @Builder
        public EStation(String title, String stationId, String stationCode) {
            this.setTitle(title);
            this.setStationId(stationId);
            this.setStationCode(stationCode);
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

}
