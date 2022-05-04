package net.uoneweb.tokyotrainnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.controller.Sections;
import net.uoneweb.tokyotrainnow.controller.TrainOnRail;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.entity.MetaData;
import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import net.uoneweb.tokyotrainnow.odpt.entity.Train;
import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;
import net.uoneweb.tokyotrainnow.repository.MetaDataRepository;
import net.uoneweb.tokyotrainnow.repository.OperatorRepository;
import net.uoneweb.tokyotrainnow.repository.RailDirectionRepository;
import net.uoneweb.tokyotrainnow.repository.RailwayRepository;
import net.uoneweb.tokyotrainnow.repository.StationRepository;
import net.uoneweb.tokyotrainnow.repository.TrainRepository;
import net.uoneweb.tokyotrainnow.repository.TrainTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        LocalDateTime now = LocalDateTime.now(clock.withZone(ZoneId.of("Asia/Tokyo")));

        updateOperators();
        updateRailDirections();
        updateRailways();
        updateStations();
        updateTrainTypes();

        updateMetaData(now);
    }

    void updateOperators() {
        final List<Operator> operators = odptApiClient.getOperators();
        operatorRepository.deleteAll();
        for (Operator operator : operators) {
            operatorRepository.save(operator);
        }
    }

    void updateRailDirections() {
        final List<RailDirection> railDirections = odptApiClient.getRailDirections();
        railDirectionRepository.deleteAll();
        for (RailDirection railDirection : railDirections) {
            railDirectionRepository.save(railDirection);
        }
    }

    void updateRailways() {
        final List<Railway> railways = odptApiClient.getRailways();
        railwayRepository.deleteAll();
        for (Railway railway : railways) {
            railwayRepository.save(railway);
        }
    }

    void updateStations() {
        final List<Station> stations = odptApiClient.getStations();
        stationRepository.deleteAll();
        for (Station station : stations) {
            stationRepository.save(station);
        }
    }

    void updateTrainTypes() {
        final List<TrainType> trainTypes = odptApiClient.getTrainTypes();
        trainTypeRepository.deleteAll();
        for (TrainType trainType : trainTypes) {
            trainTypeRepository.save(trainType);
        }
    }

    void updateMetaData(LocalDateTime date) {
        metaDataRepository.save(MetaData.builder()
                .operatorsUpdateTime(date)
                .railDirectionsUpdateTime(date)
                .railwaysUpdateTime(date)
                .stationsUpdateTime(date)
                .trainTypesUpdateTime(date)
                .build());
    }

    @Override
    public Railway getRailway(String railwayId) {
        return findRailway(railwayId);
    }

    @Override
    public CurrentRailway getCurrentRailway(String railwayId) {
        final String lang = "ja";
        final Railway railway = findRailway(railwayId);
        final Operator operator = findOperator(railway.getOperator());

        return createCurrentRailway(operator, railway, lang);
    }

    Railway findRailway(String railwayId) {
        return railwayRepository.findById(railwayId)
                .orElseThrow(() -> new RuntimeException("Could not find Railway: " + railwayId));
    }

    RailDirection findRailDirection(String railDirectionId) {
        return railDirectionRepository.findById(railDirectionId)
                .orElseThrow(() -> new RuntimeException("Could not find RailDirection: " + railDirectionId));
    }

    Operator findOperator(String operatorId) {
        return operatorRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Could not find Operator: " + operatorId));
    }

    CurrentRailway createCurrentRailway(Operator operator, Railway railway, String lang) {
        final RailDirection ascendingRailDirection = findRailDirection(railway.getAscendingRailDirection());
        final RailDirection descendingRailDirection = findRailDirection(railway.getDescendingRailDirection());

        final String operatorTitle = operator.getOperatorTitles().get(lang);
        final String ascendingTitle = ascendingRailDirection.getRailDirectionTitles().get(lang);
        final String descendingTitle = descendingRailDirection.getRailDirectionTitles().get(lang);

        final String title = railway.getTitle();
        final String color = railway.getColor();
        final String lineCode = railway.getLineCode();

        final List<Railway.Station> stations = railway.getStationOrder();
        final List<Train> trains = trainRepository.find(railway.getSameAs());
        final Sections sections = createSections(stations, lang);

        final CurrentRailway currentRailway = CurrentRailway.builder()
                .title(title)
                .ascendingTitle(ascendingTitle)
                .descendingTitle(descendingTitle)
                .operator(operatorTitle)
                .color(color)
                .lineCode(lineCode)
                .sections(sections.asList()).build();


        for (Train train : trains) {
            final TrainOnRail etrain = createTrain(train, railway, lang);
            sections.add(etrain);
        }

        final MetaData metadata = metaDataRepository.findById(1L)
                        .orElse(MetaData.builder().build());

        currentRailway.setOperatorUpdateTime(metadata.getOperatorsUpdateTime());
        currentRailway.setRailwayUpdateTime(metadata.getRailwaysUpdateTime());
        currentRailway.setTrainTypeUpdateTime(metadata.getTrainTypesUpdateTime());
        currentRailway.setTrainDate(lastTrainDate(trains));
        currentRailway.setValidSeconds(validLimit(trains));

        return currentRailway;
    }

    Sections createSections(List<Railway.Station> inputStatons, String lang) {
        Sections sections = new Sections(lang);
        List<Station> stations = findStations(inputStatons);
        for (Station station : stations) {
            sections = sections.add(station);
        }
        return sections;
    }

    List<Station> findStations(List<Railway.Station> inputStations) {
        final List<Station> stations = new ArrayList<>();
        for (Railway.Station inputStation : inputStations) {
            final String stationId = inputStation.getStation();
            final Station station = stationRepository.findById(stationId)
                    .orElseThrow(() -> new RuntimeException("Could not find Station: " + stationId));
            stations.add(station);
        }
        return Collections.unmodifiableList(stations);
    }

    Station findStation(String stationId) {
        if (!StringUtils.hasText(stationId)) {
            return Station.EMPTY;
        }
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Could not find Station: " + stationId));
    }

    TrainOnRail createTrain(Train train, Railway railway, String lang) {
        boolean ascending = isAscendingDirection(train, railway);

        final Station from = findStation(train.getFromStation());
        final Station to = findStation(train.getToStation());
        final List<Station> dest = train.getDestinationStations()
                .stream()
                .map(destId -> findStation(destId))
                .collect(Collectors.toList());

        final TrainType trainType = findTrainType(train.getTrainType());

        return TrainOnRail.builder()
                .from(from)
                .to(to)
                .destinations(dest)
                .delay(train.getDelay())
                .trainNumber(train.getTrainNumber())
                .trainType(trainType)
                .ascending(ascending)
                .carComposition(train.getCarComposition())
                .build();
    }

    TrainType findTrainType(String trainTypeId) {
        if (!StringUtils.hasText(trainTypeId)) {
            return TrainType.EMPTY;
        }
        return trainTypeRepository.findById(trainTypeId)
                .orElseThrow(() -> new RuntimeException("Could not find TrainType: " + trainTypeId));
    }

    LocalDateTime lastTrainDate(List<Train> trains) {
        return trains.stream()
                .map(t -> t.getDate())
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse(null);
    }

    long validLimit(List<Train> trains) {
        LocalDateTime now = LocalDateTime.now(clock.withZone(ZoneId.of("Asia/Tokyo")));
        LocalDateTime limit = trains.stream().map(t -> t.getValid()).sorted().findFirst()
                .orElse(now.plusMinutes(5));
        return ChronoUnit.SECONDS.between(now, limit);
    }

    boolean isAscendingDirection(Train train, Railway railway) {
        final String direction = train.getRailDirection();
        if (direction.equals(railway.getAscendingRailDirection())) {
            return true;
        } else if (direction.equals(railway.getDescendingRailDirection())) {
            return false;
        }
        log.warn("Did not match any direction: ", direction, train, railway);
        return false;
    }


}
