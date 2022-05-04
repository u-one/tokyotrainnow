package net.uoneweb.tokyotrainnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.controller.TrainOnRail;
import net.uoneweb.tokyotrainnow.controller.Sections;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
            stationRepository.save(station);
        }

        List<TrainType> trainTypes = odptApiClient.getTrainTypes();
        trainTypeRepository.deleteAll();
        for (TrainType trainType : trainTypes) {
            trainTypeRepository.save(trainType);
        }

        metaDataRepository.save(MetaData.builder()
                .operatorsUpdateTime(now)
                .railDirectionsUpdateTime(now)
                .railwaysUpdateTime(now)
                .stationsUpdateTime(now)
                .trainTypesUpdateTime(now)
                .build());

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
            log.error("raildirection is null");
        } else {
            ascendingTitle = oAscendingRailDirecton.get().getRailDirectionTitles().get(lang);
        }

        String descendingTitle = "-";
        Optional<RailDirection> oDescendingRailDirection = railDirectionRepository.findById(railway.getDescendingRailDirection());
        if (oDescendingRailDirection.isEmpty()) {
            log.error("raildirection is null");
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
        Sections sections = createSections(stations, lang);

        final String title = railway.getTitle();
        final String color = railway.getColor();
        final String lineCode = railway.getLineCode();
        return createCurrentRailway(title, ascendingTitle, descendingTitle, operatorTitle, color, lineCode, sections, railway, railwayId, lang);

    }

    Sections createSections(List<Railway.Station> stations, String lang) {
        Sections sections = new Sections(lang);
        for (int i = 0; i < stations.size(); i++) {
            String stationId = stations.get(i).getStation();
            Optional<Station> oStation = stationRepository.findById(stationId);
            if (oStation.isEmpty()) {
                log.error("Station is null", stationId);
                return sections;
            }

            Station station = oStation.get();
            sections = sections.add(station);
        }
        return sections;
    }



    CurrentRailway createCurrentRailway(String title, String ascendingTitle, String descendingTitle
            , String operatorTitle, String color, String lineCode, Sections sections, Railway railway, String railwayId, String lang) {

        final CurrentRailway currentRailway = CurrentRailway.builder()
                .title(title)
                .ascendingTitle(ascendingTitle)
                .descendingTitle(descendingTitle)
                .operator(operatorTitle)
                .color(color)
                .lineCode(lineCode)
                .sections(sections.asList()).build();

        List<Train> trains = trainRepository.find(railwayId);
        for (Train train : trains) {

            TrainOnRail etrain = createTrain(train, railway, lang);
            sections.add(etrain);
        }

        Optional<MetaData> oMetaData = metaDataRepository.findById(1L);
        MetaData metadata = oMetaData.orElse(MetaData.builder().build());

        currentRailway.setOperatorUpdateTime(metadata.getOperatorsUpdateTime());
        currentRailway.setRailwayUpdateTime(metadata.getRailwaysUpdateTime());
        currentRailway.setTrainTypeUpdateTime(metadata.getTrainTypesUpdateTime());
        currentRailway.setTrainDate(lastTrainDate(trains));
        currentRailway.setValidSeconds(validLimit(trains));

        return currentRailway;
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

    Station findStation(String stationId) {
        if (!StringUtils.hasText(stationId)) {
            return Station.EMPTY;
        }
        final Optional<Station> o = stationRepository.findById(stationId);
        if (o.isEmpty()) {
            throw new RuntimeException("Could not find Station: " + stationId);
        }
        return o.get();
    }

    TrainType findTrainType(String trainTypeId) {
        if (!StringUtils.hasText(trainTypeId)) {
            return TrainType.EMPTY;
        }
        final Optional<TrainType> o = trainTypeRepository.findById(trainTypeId);
        if (o.isEmpty()) {
            throw new RuntimeException("Could not find TrainType: " + trainTypeId);
        }
        return o.get();
    }

    LocalDateTime lastTrainDate(List<Train> trains) {
        return trains.stream().map(t -> t.getDate()).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
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
