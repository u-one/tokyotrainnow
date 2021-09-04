package net.uoneweb.tokyotrainnow.client;

import net.uoneweb.tokyotrainnow.entity.*;

import java.util.List;

public interface OdptApiClient {
    List<Operator> getOperators();

    List<Railway> getRailways();

    List<Station> getStations();

    List<TrainType> getTrainTypes();

    List<Train> getTrains();

    List<Train> getTrain(String operator, String railway);
}