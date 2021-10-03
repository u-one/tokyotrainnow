package net.uoneweb.tokyotrainnow.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MetaDataRepository {
    void setOperatorsUpdateTime(LocalDateTime time);

    void setRailwaysUpdateTime(LocalDateTime time);

    void setRailDirectionsUpdateTime(LocalDateTime time);

    void setStationsUpdateTime(LocalDateTime time);

    void setTrainTypesUpdateTime(LocalDateTime time);

    LocalDateTime getOperatorsUpdateTime();

    LocalDateTime getRailwaysUpdateTime();

    LocalDateTime getRailDirectionsUpdateTime();

    LocalDateTime getStationsUpdateTime();

    LocalDateTime getTrainTypesUpdateTime();
}
