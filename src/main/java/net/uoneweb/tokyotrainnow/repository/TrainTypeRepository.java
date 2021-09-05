package net.uoneweb.tokyotrainnow.repository;

import lombok.RequiredArgsConstructor;
import net.uoneweb.tokyotrainnow.entity.TrainType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TrainTypeRepository {
    private Map<String, TrainType> trainTypeMap = new HashMap<>();

    public void add(String trainTypeId, TrainType trainType) {
        trainTypeMap.put(trainTypeId, trainType);
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

    public TrainType findByTrainTypeId(String trainTypeId) {
        return trainTypeMap.get(trainTypeId);
    }
}