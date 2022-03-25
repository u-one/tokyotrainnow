package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.entity.MetaData;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class DefaultMetaDataRepository implements MetaDataRepository {
    private LocalDateTime operatorsUpdateTime;

    private LocalDateTime railwaysUpdateTime;

    private LocalDateTime railDirectionsUpdateTime;

    private LocalDateTime stationsUpdateTime;

    private LocalDateTime trainTypesUpdateTime;

    @Override
    public MetaData save(MetaData metaData) {
        operatorsUpdateTime = metaData.getOperatorsUpdateTime();
        railwaysUpdateTime = metaData.getRailwaysUpdateTime();
        railDirectionsUpdateTime = metaData.getRailDirectionsUpdateTime();
        stationsUpdateTime = metaData.getStationsUpdateTime();
        trainTypesUpdateTime = metaData.getTrainTypesUpdateTime();
        return metaData;
    }

    @Override
    public Optional<MetaData> findById(Long id) {
        return Optional.of(MetaData.builder()
                .operatorsUpdateTime(operatorsUpdateTime)
                .railDirectionsUpdateTime(railwaysUpdateTime)
                .railwaysUpdateTime(railDirectionsUpdateTime)
                .stationsUpdateTime(stationsUpdateTime)
                .trainTypesUpdateTime(trainTypesUpdateTime)
                .build());
    }

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
