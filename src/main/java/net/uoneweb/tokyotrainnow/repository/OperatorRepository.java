package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;

import java.util.List;
import java.util.Optional;

public interface OperatorRepository {
    Operator save(Operator operator);

    List<Operator> findAll();

    void deleteAll();

    Optional<Operator> findById(String operatorId);
}
