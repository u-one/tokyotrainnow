package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

import java.util.List;
import java.util.Optional;

public interface OperatorRepository extends DatastoreRepository<Operator, String> {
    List<Operator> findAll();

    void deleteAll();

    Optional<Operator> findById(String operatorId);
}
