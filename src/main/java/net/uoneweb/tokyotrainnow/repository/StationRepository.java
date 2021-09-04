package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.entity.Station;
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

    public Station find(String id) {
        return stationMap.get(id);
    }
}
