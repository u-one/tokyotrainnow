package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultOperatorRepository implements OperatorRepository {
    private static Map<String, Operator> map = new HashMap<>();

    @Override
    public Operator save(Operator operator) {
        map.put(operator.getSameAs(), operator);
        return operator;
    }

    @Override
    public List<Operator> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public Optional<Operator> findById(String operatorId) {
        return Optional.of(map.get(operatorId));
    }
}