package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

import java.util.List;


public interface RailwayRepository extends DatastoreRepository<Railway, String> {

    List<Railway> findAll();

    void deleteAll();

    List<Railway> findRailwaysByOperator(String operatorId);
}