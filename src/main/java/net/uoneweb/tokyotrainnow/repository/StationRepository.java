package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;

import java.util.List;
import java.util.Optional;

public interface StationRepository {
    Station save(Station station);
    List<Station> findAll();
    void deleteAll();
    Optional<Station> findById(String stationId);
}
