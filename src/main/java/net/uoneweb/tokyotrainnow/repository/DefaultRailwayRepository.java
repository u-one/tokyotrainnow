package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultRailwayRepository implements RailwayRepository {
    private static Map<String, Railway> map = new HashMap<>();

    @Override
    public Railway save(Railway railway) {
        String railwayId = railway.getSameAs();
        map.put(railwayId, railway);
        return railway;
    }

    @Override
    public List<Railway> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public List<Railway> findRailwaysByOperatorId(String operatorId) {
        return map.values().stream().filter(rw -> operatorId.equals(rw.getOperator())).collect(Collectors.toList());
    }

    @Override
    public Optional<Railway> findById(String railwayId) {
        return Optional.of(map.get(railwayId));
    }
}
