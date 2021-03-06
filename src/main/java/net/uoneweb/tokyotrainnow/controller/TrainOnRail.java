package net.uoneweb.tokyotrainnow.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class TrainOnRail {
    @NonNull
    String trainNumber;

    @NonNull
    TrainType trainType;

    int carComposition;

    int delay;

    boolean ascending;

    @JsonIgnore
    @NonNull
    Station from;

    @JsonIgnore
    @NonNull
    Station to;

    @JsonIgnore
    @NonNull
    List<Station> destinations;

    @JsonIgnore
    private LocalDateTime date;

    @JsonIgnore
    private LocalDateTime valid;

    @JsonIgnore
    @Builder.Default
    String lang = validateLanguage("ja");

    public String getTrainType() {
        return trainType.getTrainTypeTitles().get(lang);
    }

    public String getDestination() {
        if (destinations.isEmpty()) {
            return "-";
        } else {
            return destinations.stream()
                    .map(station -> station.getStationTitle())
                    .map(titles -> titles.get(lang))
                    .collect(Collectors.joining("・"));
        }
    }

    private static String validateLanguage(String lang) {
        Set<String> supportedLangs = Set.of("ja", "en");
        if (!supportedLangs.contains(lang)) {
            return "en";
        }
        return lang;
    }

    public String getFrom() {
       return from.getTitle();
    }

    public String getTo() {
        return to.getTitle();
    }
}
