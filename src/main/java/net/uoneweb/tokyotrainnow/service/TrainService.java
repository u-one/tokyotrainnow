package net.uoneweb.tokyotrainnow.service;

import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;

public interface TrainService {
    void update();
    Railway getRailway(String railwayId);

    CurrentRailway getCurrentRailway(String railwayId);
}
