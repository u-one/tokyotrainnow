package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultRailwayRepository {
    private static Map<String, Railway> map = new HashMap<>();

    public void save(Railway railway) {
        String railwayId = railway.getSameAs();
        map.put(railwayId, railway);
    }

    public List<Railway> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        map.clear();
    }

    public List<Railway> findRailwaysByOperatorId(String operatorId) {
        return map.values().stream().filter(rw -> operatorId.equals(rw.getOperator())).collect(Collectors.toList());
    }

    public Optional<Railway> findById(String railwayId) {
        return Optional.of(map.get(railwayId));
    }
}
