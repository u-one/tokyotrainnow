package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;

import java.util.Optional;

public interface RailDirectionRepository {
    RailDirection save(RailDirection railDirection);
    void deleteAll();
    Optional<RailDirection> findById(String railDirectionId);
}
