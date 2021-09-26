package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StationRepository {
    private static Map<String, Station> stationMap = new HashMap<>();

    public void add(String stationId, Station station) {
        stationMap.put(stationId, station);
    }

    public List<Station> findAll() {
        return stationMap.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        stationMap.clear();
    }

    public Station findByStationId(String stationId) {
        return stationMap.get(stationId);
    }
}