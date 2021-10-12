package net.uoneweb.tokyotrainnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.*;
import net.uoneweb.tokyotrainnow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTrainService implements TrainService {
    @Autowired
    Clock clock;

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Autowired
    private OdptApiClient odptApiClient;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private DefaultRailDirectionRepository railDirectionRepository;

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
        LocalDateTime now = LocalDateTime.now(clock.withZone(ZoneId.of("Asia/Tokyo")));

        List<Operator> operators = odptApiClient.getOperators();
        operatorRepository.deleteAll();
        for (Operator operator : operators) {
            operatorRepository.save(operator);
        }

        List<RailDirection> railDirections = odptApiClient.getRailDirections();
        railDirectionRepository.deleteAll();
        for (RailDirection railDirection : railDirections) {
            railDirectionRepository.save(railDirection);
        }

        List<Railway> railways = odptApiClient.getRailways();
        railwayRepository.deleteAll();
        for (Railway railway : railways) {
            railwayRepository.save(railway);
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

        metaDataRepository.setOperatorsUpdateTime(now);
        metaDataRepository.setRailDirectionsUpdateTime(now);
        metaDataRepository.setRailwaysUpdateTime(now);
        metaDataRepository.setStationsUpdateTime(now);
        metaDataRepository.setTrainTypesUpdateTime(now);
    }

    @Override
    public Railway getRailway(String railwayId) {
        return railwayRepository.findById(railwayId).orElse(null);
    }

    @Override
    public CurrentRailway getCurrentRailway(String railwayId) {
        final String lang = "ja";

        Optional<Railway> oRailway = railwayRepository.findById(railwayId);
        if (oRailway.isEmpty()) {
            log.error("Railway is null", railwayId);
            return null;
        }

        Railway railway = oRailway.get();

        String ascendingTitle = "-";
        Optional<RailDirection> oAscendingRailDirecton = railDirectionRepository.findById(railway.getAscendingRailDirection());
        if (oAscendingRailDirecton.isEmpty()) {
            log.error("raildirection is null", railway.getAscendingRailDirection());
        } else {
            ascendingTitle = oAscendingRailDirecton.get().getRailDirectionTitles().get(lang);
        }

        String descendingTitle = "-";
        Optional<RailDirection> oDescendingRailDirection = railDirectionRepository.findById(railway.getDescendingRailDirection());
        if (oDescendingRailDirection.isEmpty()) {
            log.error("raildirection is null", railway.getDescendingRailDirection());
        } else {
            descendingTitle = oDescendingRailDirection.get().getRailDirectionTitles().get(lang);
        }

        Optional<Operator> oOperator = operatorRepository.findById(railway.getOperator());
        String operatorTitle = "";
        if (oOperator.isEmpty()) {
            log.error("operator is null", railway.getOperator());
        } else {
            operatorTitle = oOperator.get().getOperatorTitles().get(lang);
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

        List<Train> trains = trainRepository.find(railwayId);
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

        currentRailway.setOperatorUpdateTime(metaDataRepository.getOperatorsUpdateTime());
        currentRailway.setRailwayUpdateTime(metaDataRepository.getRailwaysUpdateTime());
        currentRailway.setTrainTypeUpdateTime(metaDataRepository.getTrainTypesUpdateTime());
        currentRailway.setTrainDate(lastTrainDate(trains));

        return currentRailway;
    }

    LocalDateTime lastTrainDate(List<Train> trains) {
        return trains.stream().map(t -> t.getDate()).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
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
