package net.uoneweb.tokyotrainnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.*;
import net.uoneweb.tokyotrainnow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTrainService implements TrainService {

    @Autowired
    private OdptApiClient odptApiClient;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RailDirectionRepository railDirectionRepository;

    @Autowired
    private RailwayRepository railwayRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainTypeRepository trainTypeRepository;

    @Override
    public void update() {
        List<Operator> operators = odptApiClient.getOperators();
        operatorRepository.deleteAll();
        for (Operator operator : operators) {
            operatorRepository.add(operator.getSameAs(), operator);
        }

        List<RailDirection> railDirections = odptApiClient.getRailDirections();
        railDirectionRepository.deleteAll();
        for (RailDirection railDirection : railDirections) {
            railDirectionRepository.add(railDirection.getSameAs(), railDirection);
        }

        List<Railway> railways = odptApiClient.getRailways();
        railwayRepository.deleteAll();
        for (Railway railway : railways) {
            railwayRepository.add(railway.getSameAs(), railway);
        }

        List<Station> stations = odptApiClient.getStations();
        stationRepository.deleteAll();
        for (Station station : stations) {
            stationRepository.add(station.getSameAs(), station);
        }

        List<TrainType> trainTypes = odptApiClient.getTrainTypes();
        trainTypeRepository.deleteAll();
        for (TrainType trainType : trainTypes) {
            trainTypeRepository.add(trainType.getSameAs(), trainType);
        }
    }

    @Override
    public Railway getRailway(String railwayId) {
        return railwayRepository.findByRailwayId(railwayId);
    }

    @Override
    public CurrentRailway getCurrentRailway(String railwayId) {
        final String lang = "ja";

        Railway railway = railwayRepository.findByRailwayId(railwayId);
        if (Objects.isNull(railway)) {
            log.error("Railway is null", railwayId);
            return null;
        }

        String ascendingTitle = "-";
        RailDirection ascendingRailDirecton = railDirectionRepository.find(railway.getAscendingRailDirection());
        if (Objects.isNull(ascendingRailDirecton)) {
            log.error("raildirection is null", railway.getAscendingRailDirection());
        } else {
            ascendingTitle = ascendingRailDirecton.getRailDirectionTitles().get(lang);
        }

        String descendingTitle = "-";
        RailDirection descendingRailDirection = railDirectionRepository.find(railway.getDescendingRailDirection());
        if (Objects.isNull(ascendingRailDirecton)) {
            log.error("raildirection is null", railway.getDescendingRailDirection());
        } else {
            descendingTitle = descendingRailDirection.getRailDirectionTitles().get(lang);
        }

        Operator operator = operatorRepository.findByOperatorId(railway.getOperator());
        String operatorTitle = "";
        if (Objects.isNull(operator)) {
            log.error("operator is null", railway.getOperator());
        } else {
            operatorTitle = operator.getOperatorTitles().get(lang);
        }

        List<Railway.Station> stations = railway.getStationOrder();
        List<CurrentRailway.Section> sections = new ArrayList<>();
        for (int i = 0; i < stations.size(); i++) {
            String stationId = stations.get(i).getStation();
            Station station = stationRepository.findByStationId(stationId);
            if (Objects.isNull(station)) {
                log.error("Station is null", stationId);
                return null;
            }

            sections.add(CurrentRailway.Station.builder()
                    .title(station.getStationTitle().get(lang))
                    .stationId(station.getSameAs())
                    .stationCode(station.getStationCode())
                    .odptStation(station).build());
            if (i < stations.size() - 1) {
                sections.add(CurrentRailway.Line.builder()
                        .title("|")
                        .build());
            }
        }

        CurrentRailway currentRailway = CurrentRailway.builder()
                .title(railway.getTitle())
                .ascendingTitle(ascendingTitle)
                .descendingTitle(descendingTitle)
                .operator(operatorTitle)
                .color(railway.getColor())
                .lineCode(railway.getLineCode())
                .sections(sections).build();

        List<Train> trains = trainRepository.find("odpt.Operator:JR-East", railwayId);
        for (Train train : trains) {
            boolean ascending = isAscendingDirection(train, railway);
            final String from = train.getFromStation();
            final String to = train.getToStation();

            final String dest = train.getDestinationStations().stream().map(
                    destId -> stationRepository.findByStationId(destId))
                .map(station -> {
                    String title = "-";
                    if (Objects.nonNull(station)) {
                        title = station.getStationTitle().get(lang);
                    }
                    return title;
                }).collect(Collectors.joining("・"));

            int index = findIndex(sections, from, to, ascending);

            final String trainType = trainTypeRepository.findByTrainTypeId(train.getTrainType()).getTrainTypeTitles().get("ja");

            sections.get(index).addTrain(CurrentRailway.Train.builder()
                            .destination(dest)
                            .delay(train.getDelay())
                            .trainNumber(train.getTrainNumber())
                            .trainType(trainType)
                            .ascending(ascending)
                            .carComposition(train.getCarComposition())
                    .build());
        }

        for (CurrentRailway.Section section : sections) {
            log.info(section.toString());
        }

        return currentRailway;
    }

    boolean isAscendingDirection(Train train, Railway railway) {
        final String direction = train.getRailDirection();
        if (direction.equals(railway.getAscendingRailDirection())) {
            return true;
        }
        else if (direction.equals(railway.getDescendingRailDirection())) {
            return false;
        }
        log.warn("Did not match any direction: ", direction, train, railway);
        return false;
    }

    int findIndex(List<CurrentRailway.Section> sections, String from, String to, boolean ascending) {
        if (ascending) {
            for (int i = 0; i < sections.size(); i++) {
                CurrentRailway.Section section = sections.get(i);
                if (!section.getStationId().equals(from)) {
                    continue;
                }
                if (to == null) {
                    return i;
                }
                int lineIndex = i + 1;
                if (lineIndex >= sections.size()) {
                    log.error("Section検出エラー(不正なインデックス)", lineIndex, from, to, sections);
                }
                return lineIndex;
            }
        } else {
            for (int i = sections.size() - 1; i >= 0 ; i--) {
                CurrentRailway.Section section = sections.get(i);
                if (!section.getStationId().equals(from)) {
                    continue;
                }
                if (to == null) {
                    return i;
                }
                int lineIndex = i - 1;
                if (lineIndex < 0) {
                    log.error("Section検出エラー(不正なインデックス)", lineIndex, from, to, sections);
                }
                return lineIndex;
            }
        }
        log.error("Section検出エラー(一致なし)", from, to, sections);
        return 0;
    }
}
