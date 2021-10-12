package net.uoneweb.tokyotrainnow.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class DefaultMetaDataRepository {
    private LocalDateTime operatorsUpdateTime;

    private LocalDateTime railwaysUpdateTime;

    private LocalDateTime railDirectionsUpdateTime;

    private LocalDateTime stationsUpdateTime;

    private LocalDateTime trainTypesUpdateTime;

    public LocalDateTime getOperatorsUpdateTime() {
        return operatorsUpdateTime;
    }

    public void setOperatorsUpdateTime(LocalDateTime operatorsUpdateTime) {
        this.operatorsUpdateTime = operatorsUpdateTime;
    }

    public LocalDateTime getRailwaysUpdateTime() {
        return railwaysUpdateTime;
    }

    public void setRailwaysUpdateTime(LocalDateTime railwaysUpdateTime) {
        this.railwaysUpdateTime = railwaysUpdateTime;
    }

    public LocalDateTime getRailDirectionsUpdateTime() {
        return railDirectionsUpdateTime;
    }

    public void setRailDirectionsUpdateTime(LocalDateTime railDirectionsUpdateTime) {
        this.railDirectionsUpdateTime = railDirectionsUpdateTime;
    }

    public LocalDateTime getStationsUpdateTime() {
        return stationsUpdateTime;
    }

    public void setStationsUpdateTime(LocalDateTime stationsUpdateTime) {
        this.stationsUpdateTime = stationsUpdateTime;
    }

    public LocalDateTime getTrainTypesUpdateTime() {
        return trainTypesUpdateTime;
    }

    public void setTrainTypesUpdateTime(LocalDateTime trainTypesUpdateTime) {
        this.trainTypesUpdateTime = trainTypesUpdateTime;
    }
}
