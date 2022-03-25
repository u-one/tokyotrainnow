package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;

import java.util.List;
import java.util.Optional;

public interface TrainTypeRepository {
    TrainType save(TrainType trainType);
    List<TrainType> findAll();
    void deleteAll();
    List<TrainType> findByOperatorId(String operatorId);
    Optional<TrainType> findById(String trainTypeId);
}
