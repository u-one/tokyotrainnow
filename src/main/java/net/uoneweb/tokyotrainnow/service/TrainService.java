package net.uoneweb.tokyotrainnow.service;

import net.uoneweb.tokyotrainnow.entity.Operator;
import net.uoneweb.tokyotrainnow.entity.Railway;
import net.uoneweb.tokyotrainnow.entity.Station;
import net.uoneweb.tokyotrainnow.entity.Train;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TrainService {
    void initialize();
    List<Operator> getOperators();
    List<Railway> getRailways();
    List<Railway> getRailways(String operatorId);
    Railway getRailway(String railwayId);
    List<Station> getStations();
    Optional<Station> getStation(String id);
    List<Train> getTrains();
    List<Train> getTrain(String operator, String railway);
}
