package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class DefaultRailDirectionRepository implements RailDirectionRepository {
    private static Map<String, RailDirection> map = new HashMap<>();

    @Override
    public RailDirection save(RailDirection railDirection) {
        map.put(railDirection.getSameAs(), railDirection);
        return railDirection;
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public Optional<RailDirection> findById(String railDirectionId) {
        if (!StringUtils.hasText(railDirectionId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(map.get(railDirectionId));
    }
}
