package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultStationRepository implements StationRepository {
    private static Map<String, Station> stationMap = new HashMap<>();

    @Override
    public Station save(Station station) {
        stationMap.put(station.getSameAs(), station);
        return station;
    }

    @Override
    public List<Station> findAll() {
        return stationMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        stationMap.clear();
    }

    @Override
    public Optional<Station> findById(String stationId) {
        return Optional.of(stationMap.get(stationId));
    }
}