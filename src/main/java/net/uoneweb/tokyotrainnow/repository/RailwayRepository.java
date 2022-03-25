package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Railway;

import java.util.List;
import java.util.Optional;

public interface RailwayRepository {
    Railway save(Railway railway);

    List<Railway> findAll();

    void deleteAll();

    List<Railway> findRailwaysByOperatorId(String operatorId);

    Optional<Railway> findById(String railwayId);
}
