package net.uoneweb.tokyotrainnow.service;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.client.DefaultOdptApiClient;
import net.uoneweb.tokyotrainnow.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.entity.*;
import net.uoneweb.tokyotrainnow.repository.OperatorRepository;
import net.uoneweb.tokyotrainnow.repository.RailwayRepository;
import net.uoneweb.tokyotrainnow.repository.StationRepository;
import net.uoneweb.tokyotrainnow.repository.TrainTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DefaultTrainService implements TrainService {

    @Autowired
    private RestOperations restOperations;

    @Autowired
    private OdptApiClient apiClient;

    @Autowired
    private TrainTypeRepository trainTypeRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RailwayRepository railwayRepository;

    @Autowired
    private StationRepository stationRepository;

    @Override
    public void initialize() {

        List<Operator> operators = apiClient.getOperators();
        operators.stream().forEach(o -> operatorRepository.add(o.getSameAs(), o));

        List<Railway> railways = apiClient.getRailways();
        railways.stream().forEach(r -> railwayRepository.add(r.getSameAs(), r));

        List<Station> stations = apiClient.getStations();
        stations.stream().forEach(s -> stationRepository.add(s.getSameAs(), s));

        List<TrainType> trainTypes = apiClient.getTrainTypes();
        trainTypes.stream().forEach(t -> trainTypeRepository.add(t.getSameAs(), t));
    }

    @Override
    public List<Operator> getOperators() {
        return operatorRepository.findAll();
    }

    @Override
    public List<Railway> getRailways() {
        return railwayRepository.findAll();
    }

    @Override
    public List<Railway> getRailways(String operatorId) {
        return railwayRepository.findAllByOperatorId(operatorId);
    }

    @Override
    public Railway getRailway(String railwayId) {
        return railwayRepository.find(railwayId);
    }

    @Override
    public List<Station> getStations() {
        return stationRepository.findAll();
    }

    @Override
    public Optional<Station> getStation(String id) {
        return Optional.ofNullable(stationRepository.find(id));
    }

    @Override
    public List<Train> getTrains() {
        return apiClient.getTrains();
    }

    @Override
    public List<Train> getTrain(String operator, String railway) {
        return apiClient.getTrain(operator, railway);
    }
}
