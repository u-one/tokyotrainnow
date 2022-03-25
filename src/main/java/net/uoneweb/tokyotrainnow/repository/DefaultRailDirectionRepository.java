package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DefaultRailDirectionRepository implements RailDirectionRepository {
    private static Map<String, RailDirection> map = new HashMap<>();

    @Override
    public void save(RailDirection railDirection) {
        map.put(railDirection.getSameAs(), railDirection);
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public Optional<RailDirection> findById(String railDirectionId) {
        if (Objects.isNull(railDirectionId)) {
            return Optional.empty();
        }
        return Optional.of(map.get(railDirectionId));
    }
}
