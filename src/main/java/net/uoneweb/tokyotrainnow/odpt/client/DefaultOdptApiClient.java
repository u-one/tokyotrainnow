package net.uoneweb.tokyotrainnow.odpt.client;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.odpt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DefaultOdptApiClient implements OdptApiClient {
    @Autowired
    private OdptApiClientConfig config;

    @Autowired
    private RestOperations restOperations;

    public List<Operator> getOperators() {
        String url = config.getEndpoint() + "odpt:Operator.json?"
                + "acl:consumerKey=" + config.getKey();

        ResponseEntity<Operator[]> res = restOperations.getForEntity(url, Operator[].class);
        return Arrays.asList(res.getBody());
    }

    public List<Railway> getRailways() {
        String url = config.getEndpoint() + "odpt:Railway.json?"
                + "acl:consumerKey=" + config.getKey();

        ResponseEntity<Railway[]> res = restOperations.getForEntity(url, Railway[].class);
        return Arrays.asList(res.getBody());
    }

    public List<Station> getStations() {
        String url = config.getEndpoint() + "odpt:Station.json?"
                + "acl:consumerKey=" + config.getKey();

        ResponseEntity<Station[]> res = restOperations.getForEntity(url, Station[].class);
        Station[] stations = res.getBody();
        return Arrays.asList(stations);
    }

    public List<TrainType> getTrainTypes() {
        String url = config.getEndpoint() + "odpt:TrainType.json?"
                + "acl:consumerKey=" + config.getKey();

        ResponseEntity<TrainType[]> res = restOperations.getForEntity(url, TrainType[].class);
        TrainType[] trainTypes = res.getBody();
        return Arrays.asList(trainTypes);
    }

    @Override
    public List<RailDirection> getRailDirections() {
        String url = config.getEndpoint() + "odpt:RailDirection.json?"
                + "acl:consumerKey=" + config.getKey();
        ResponseEntity<RailDirection[]> res = restOperations.getForEntity(url, RailDirection[].class);
        RailDirection[] railDirections = res.getBody();
        return Arrays.asList(railDirections);
    }

    public List<Train> getTrains() {
        String operator = "odpt.Operator:JR-East";
        String railway = "odpt.Railway:JR-East.SobuRapid";
        String url = config.getEndpoint() + "odpt:Train?"
                + "acl:consumerKey=" + config.getKey()
                + "&odpt:operator=" + operator
                + "&odpt:railway=" + railway;

        ResponseEntity<Train[]> res = restOperations.getForEntity(url, Train[].class);
        Train[] trains = res.getBody();

        log.info(trains.toString());
        for (Train train : trains) {
            log.info(train.toString());
        }

        return Arrays.asList(trains);
    }

    public List<Train> getTrain(String operator, String railway) {
        String url = config.getEndpoint() +"odpt:Train?"
                + "acl:consumerKey=" + config.getKey()
                + "&odpt:operator=" + operator
                + "&odpt:railway=" + railway;

        ResponseEntity<Train[]> res = restOperations.getForEntity(url, Train[].class);
        Train[] trains = res.getBody();

        log.info(trains.toString());
        for (Train train : trains) {
            log.info(train.toString());
        }

        return Arrays.asList(trains);
    }
}