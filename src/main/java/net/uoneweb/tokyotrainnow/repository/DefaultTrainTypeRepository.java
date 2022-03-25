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
public class DefaultTrainTypeRepository implements TrainTypeRepository {
    private Map<String, TrainType> trainTypeMap = new HashMap<>();

    @Override
    public TrainType save(TrainType trainType) {
        trainTypeMap.put(trainType.getSameAs(), trainType);
        return trainType;
    }

    @Override
    public List<TrainType> findAll() {
        return trainTypeMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        trainTypeMap.clear();
    }

    @Override
    public List<TrainType> findByOperatorId(String operatorId) {
        List<TrainType> trainTypes = trainTypeMap.values().stream().filter(t -> t.getOperator() == operatorId)
                .collect(Collectors.toList());
        return trainTypes;
    }

    @Override
    public Optional<TrainType> findById(String trainTypeId) {
        return Optional.of(trainTypeMap.get(trainTypeId));
    }
}