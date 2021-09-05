package net.uoneweb.tokyotrainnow.client;

import net.uoneweb.tokyotrainnow.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DefaultOdptApiClientTest {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DefaultOdptApiClient odptApiClient;

    @Value("classpath:/odpt_api_client/operators.jsonld")
    Resource operatorsFile;

    @Value("classpath:/odpt_api_client/railways.jsonld")
    Resource railwaysFile;

    @Value("classpath:/odpt_api_client/stations.jsonld")
    Resource stationsFile;

    @Value("classpath:/odpt_api_client/traintypes.jsonld")
    Resource trainTypesFile;

    @Value("classpath:/odpt_api_client/trains.jsonld")
    Resource trainsFile;

    @Test
    public void getOperatorSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:Operator.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(operatorsFile, MediaType.APPLICATION_JSON));

        List<Operator> operators = odptApiClient.getOperators();
        List<String> titles = operators.stream().map(o -> o.getTitle()).sorted().collect(Collectors.toList());
        List<String> expected = List.of(
                "東京国際空港ターミナル",
                "東京都交通局",
                "JR東日本",
                "小田急電鉄",
                "東京メトロ",
                "全日本空輸",
                "小田急バス"
        ).stream().sorted().collect(Collectors.toList());
        assertIterableEquals(expected, titles);
    }

    @Test
    public void getRailwaysSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:Railway.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(railwaysFile, MediaType.APPLICATION_JSON));

        List<Railway> railways = odptApiClient.getRailways();
        Map<String, Railway> map = railways.stream().collect(Collectors.toMap(
                r -> r.getSameAs(),
                r -> r
        ));
        assertEquals(map.get("odpt.Railway:Odakyu.Odawara").getTitle(), "小田原線");
        assertEquals(map.get("odpt.Railway:JR-East.SobuRapid").getStationOrder().size(), 10);
        assertEquals(map.get("odpt.Railway:TokyoMetro.Chiyoda").getRailwayTitles().get("en"), "Chiyoda Line");
    }

    @Test
    public void getStationsSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:Station.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(stationsFile, MediaType.APPLICATION_JSON));

        List<Station> stations = odptApiClient.getStations();
        Map<String, Station> map = stations.stream().collect(Collectors.toMap(
                s -> s.getSameAs(),
                s -> s
        ));
        assertEquals("成城学園前", map.get("odpt.Station:Odakyu.Odawara.SeijogakuenMae").getTitle());
        assertEquals("舞浜", map.get("odpt.Station:JR-East.Keiyo.Maihama").getTitle());
        assertEquals("表参道", map.get("odpt.Station:TokyoMetro.Chiyoda.OmoteSando").getTitle());

        Station shinjuku = map.get("odpt.Station:JR-East.ChuoRapid.Shinjuku");
        assertEquals("新宿", shinjuku.getTitle());
        assertEquals("odpt.Railway:JR-East.ChuoRapid", shinjuku.getRailway());
        assertEquals("odpt.Operator:JR-East", shinjuku.getOperator());
        assertEquals("JC05", shinjuku.getStationCode());
        assertEquals(35.69018, shinjuku.getGeo_lat());
        assertEquals(139.70038, shinjuku.getGeo_long());
        assertEquals("Shinjuku", shinjuku.getStationTitle().get("en"));
        assertEquals(1, shinjuku.getPassengerSurveys().size());
        assertEquals(4, shinjuku.getStationTimetables().size());
        assertEquals(11, shinjuku.getConnectingRailways().size());
    }

    @Test
    public void getTrainTypesSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:TrainType.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(trainTypesFile, MediaType.APPLICATION_JSON));

        List<TrainType> trainTypes = odptApiClient.getTrainTypes();
        Map<String, TrainType> map = trainTypes.stream().collect(Collectors.toMap(
                t -> t.getSameAs(),
                t -> t
        ));
        assertEquals("快速", map.get("odpt.TrainType:JR-East.Rapid").getTitle());
        assertEquals(2, map.get("odpt.TrainType:TokyoMetro.SemiExpress").getTrainTypeTitles().size());
    }

    @Test
    public void getTrainsSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        String operator = "odpt.Operator:JR-East";
        String railway = "odpt.Railway:JR-East.SobuRapid";

        server.expect(requestTo("http://localhost:8081/odpt:Train?"
                        + "acl:consumerKey=TEST-KEY"
                        + "&odpt:operator=" + operator
                        + "&odpt:railway=" + railway))
                .andRespond(withSuccess(trainsFile, MediaType.APPLICATION_JSON));

        List<Train> trains = odptApiClient.getTrains();
        assertEquals(2, trains.size());
    }

    @Test
    public void getTrainSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        String operator = "odpt.Operator:JR-East";
        String railway = "odpt.Railway:JR-East.SobuRapid";

        server.expect(requestTo("http://localhost:8081/odpt:Train?"
                        + "acl:consumerKey=TEST-KEY"
                        + "&odpt:operator=" + operator
                        + "&odpt:railway=" + railway))
                .andRespond(withSuccess(trainsFile, MediaType.APPLICATION_JSON));

        List<Train> trains = odptApiClient.getTrain(operator, railway);
        assertEquals(2, trains.size());
    }
}