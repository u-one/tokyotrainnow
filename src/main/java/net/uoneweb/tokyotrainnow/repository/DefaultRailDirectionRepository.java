package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class DefaultRailDirectionRepository {
    private static Map<String, RailDirection> map = new HashMap<>();

    public void save(RailDirection railDirection) {
        map.put(railDirection.getSameAs(), railDirection);
    }

    public void deleteAll() {
        map.clear();
    }

    public Optional<RailDirection> findById(String railDirectionId) {
        return Optional.of(map.get(railDirectionId));
    }
}
