package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RailDirectionRepository {
    private static Map<String, RailDirection> map = new HashMap<>();

    public void add(String railDirectionId, RailDirection railDirection) {
        map.put(railDirectionId, railDirection);
    }

    public void deleteAll() {
        map.clear();
    }

    public RailDirection find(String railDirectionId) {
        return map.get(railDirectionId);
    }
}
