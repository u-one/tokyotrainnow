package net.uoneweb.tokyotrainnow.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class DefaultMetaDataRepository implements MetaDataRepository {
    private LocalDateTime operatorsUpdateTime;

    private LocalDateTime railwaysUpdateTime;

    private LocalDateTime railDirectionsUpdateTime;

    private LocalDateTime stationsUpdateTime;

    private LocalDateTime trainTypesUpdateTime;

    @Override
    public LocalDateTime getOperatorsUpdateTime() {
        return operatorsUpdateTime;
    }

    @Override
    public void setOperatorsUpdateTime(LocalDateTime operatorsUpdateTime) {
        this.operatorsUpdateTime = operatorsUpdateTime;
    }

    @Override
    public LocalDateTime getRailwaysUpdateTime() {
        return railwaysUpdateTime;
    }

    @Override
    public void setRailwaysUpdateTime(LocalDateTime railwaysUpdateTime) {
        this.railwaysUpdateTime = railwaysUpdateTime;
    }

    @Override
    public LocalDateTime getRailDirectionsUpdateTime() {
        return railDirectionsUpdateTime;
    }

    @Override
    public void setRailDirectionsUpdateTime(LocalDateTime railDirectionsUpdateTime) {
        this.railDirectionsUpdateTime = railDirectionsUpdateTime;
    }

    @Override
    public LocalDateTime getStationsUpdateTime() {
        return stationsUpdateTime;
    }

    @Override
    public void setStationsUpdateTime(LocalDateTime stationsUpdateTime) {
        this.stationsUpdateTime = stationsUpdateTime;
    }

    @Override
    public LocalDateTime getTrainTypesUpdateTime() {
        return trainTypesUpdateTime;
    }

    @Override
    public void setTrainTypesUpdateTime(LocalDateTime trainTypesUpdateTime) {
        this.trainTypesUpdateTime = trainTypesUpdateTime;
    }
}
