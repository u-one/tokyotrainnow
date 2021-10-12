package net.uoneweb.tokyotrainnow.repository;

import lombok.RequiredArgsConstructor;
import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DefaultTrainTypeRepository {
    private Map<String, TrainType> trainTypeMap = new HashMap<>();

    public void save(TrainType trainType) {
        trainTypeMap.put(trainType.getSameAs(), trainType);
    }

    public List<TrainType> findAll() {
        return trainTypeMap.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        trainTypeMap.clear();
    }

    public List<TrainType> findByOperatorId(String operatorId) {
        List<TrainType> trainTypes = trainTypeMap.values().stream().filter(t -> t.getOperator() == operatorId)
                .collect(Collectors.toList());
        return trainTypes;
    }

    public Optional<TrainType> findById(String trainTypeId) {
        return Optional.of(trainTypeMap.get(trainTypeId));
    }
}