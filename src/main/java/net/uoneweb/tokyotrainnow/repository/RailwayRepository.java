package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.entity.Railway;
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

    public List<Railway> findAllByOperatorId(String operatorId) {
        return map.values().stream().filter(rw -> operatorId.equals(rw.getOperator())).collect(Collectors.toList());
    }

    public Railway find(String id) {
        return map.get(id);
    }
}
