package net.uoneweb.tokyotrainnow.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Trains {
    private final List<TrainOnRail> trainList;

    public Trains() {
        trainList = new ArrayList<>();
    }

    private Trains(final List<TrainOnRail> trainList) {
        this.trainList = trainList;
    }

    public Trains add(TrainOnRail train) {
        final List<TrainOnRail> trainList = new ArrayList<>(this.trainList);
        trainList.add(train);
        return new Trains(trainList);
    }

    public List<TrainOnRail> asList() {
        return Collections.unmodifiableList(trainList);
    }

    public LocalDateTime lastTrainDate() {
        return trainList.stream()
                .map(t -> t.getDate())
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse(null);
    }

    public LocalDateTime validLimit() {
        return trainList.stream().map(t -> t.getValid()).sorted().findFirst()
                .orElse(null);

    }

}
