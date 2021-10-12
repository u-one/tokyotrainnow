package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultOperatorRepository {
    private static Map<String, Operator> map = new HashMap<>();

    public void save(Operator operator) {
        map.put(operator.getSameAs(), operator);
    }

    public List<Operator> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        map.clear();
    }

    public Optional<Operator> findById(String operatorId) {
        return Optional.of(map.get(operatorId));
    }
}