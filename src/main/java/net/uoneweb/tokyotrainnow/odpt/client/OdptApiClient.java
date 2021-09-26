package net.uoneweb.tokyotrainnow.odpt.client;

import net.uoneweb.tokyotrainnow.odpt.entity.*;

import java.util.List;

public interface OdptApiClient {
    List<Operator> getOperators();

    List<Railway> getRailways();

    List<Station> getStations();

    List<TrainType> getTrainTypes();

    List<RailDirection> getRailDirections();

    List<Train> getTrains();

    List<Train> getTrain(String operator, String railway);
}
