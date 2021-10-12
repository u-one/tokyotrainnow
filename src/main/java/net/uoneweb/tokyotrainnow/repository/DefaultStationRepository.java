package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultStationRepository {
    private static Map<String, Station> stationMap = new HashMap<>();

    public void save(Station station) {
        stationMap.put(station.getSameAs(), station);
    }

    public List<Station> findAll() {
        return stationMap.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        stationMap.clear();
    }

    public Optional<Station> findById(String stationId) {
        return Optional.of(stationMap.get(stationId));
    }
}