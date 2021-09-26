package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OperatorRepository {
    private static Map<String, Operator> map = new HashMap<>();

    public void add(String operatorId, Operator operator) {
        map.put(operatorId, operator);
    }

    public List<Operator> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        map.clear();;
    }

    public Operator findByOperatorId(String operatorId) {
        return map.get(operatorId);
    }
}