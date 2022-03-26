package net.uoneweb.tokyotrainnow.repository.gcp;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
//import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

import java.util.List;


public interface GcpRailwayRepository // extends DatastoreRepository<Railway, String>
{

    List<Railway> findAll();

    void deleteAll();

    List<Railway> findRailwaysByOperator(String operatorId);
}