package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RailwayRepository {
    private static Map<String, Railway> map = new HashMap<>();

    public void add(String railwayId, Railway railway) {
        map.put(railwayId, railway);
    }

    public List<Railway> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        map.clear();
    }

    public List<Railway> findByOperatorId(String operatorId) {
        return map.values().stream().filter(rw -> operatorId.equals(rw.getOperator())).collect(Collectors.toList());
    }

    public Railway findByRailwayId(String railwayId) {
        return map.get(railwayId);
    }
}