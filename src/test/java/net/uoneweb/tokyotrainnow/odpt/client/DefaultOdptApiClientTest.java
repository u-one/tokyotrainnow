package net.uoneweb.tokyotrainnow.odpt.client;

import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.odpt.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Value("classpath:/odpt_api_client/raildirections.jsonld")
    Resource railDirectionFile;

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

        assertThat(operators).contains(Operator.builder()
                        .id("urn:ucode:_00001C000000000000010000030E6606")
                        .sameAs("odpt.Operator:JR-East")
                        .date(LocalDateTime.of(2019,04, 22, 15,00,00))
                        .title("JR東日本")
                        .operatorTitles(Map.of("en", "JR East","ja", "JR東日本"))
                .build());
    }

    @Test
    public void getRailwaysSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:Railway.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(railwaysFile, MediaType.APPLICATION_JSON));

        List<Railway> railways = odptApiClient.getRailways();

        assertThat(railways).extracting(r -> r.getSameAs())
                .containsExactly("odpt.Railway:Odakyu.Odawara", "odpt.Railway:JR-East.SobuRapid", "odpt.Railway:TokyoMetro.Chiyoda");

        assertThat(railways).contains(TestDataBuilder.soubuRapid());
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
        assertThat(shinjuku).isEqualTo(
                Station.builder()
                        .id("urn:ucode:_00001C000000000000010000031027F5")
                        .date(LocalDateTime.of(2021,6,21,14,00,00))
                        .title("新宿")
                        .sameAs("odpt.Station:JR-East.ChuoRapid.Shinjuku")
                        .railway("odpt.Railway:JR-East.ChuoRapid")
                        .operator("odpt.Operator:JR-East")
                        .stationCode("JC05")
                        .stationTitle(Map.of("en", "Shinjuku", "ja", "新宿"))
                        .geo_lat(35.69018)
                        .geo_long(139.70038)
                        .passengerSurveys(List.of("odpt.PassengerSurvey:JR-East.Shinjuku"))
                        .stationTimetables(List.of(
                                "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Inbound.Weekday",
                                "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Inbound.SaturdayHoliday",
                                "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Outbound.Weekday",
                                "odpt.StationTimetable:JR-East.ChuoRapid.Shinjuku.Outbound.SaturdayHoliday"
                        ))
                        .connectingRailways(List.of(
                                "odpt.Railway:JR-East.ChuoSobuLocal",
                                "odpt.Railway:JR-East.SaikyoKawagoe",
                                "odpt.Railway:JR-East.ShonanShinjuku",
                                "odpt.Railway:JR-East.Yamanote",
                                "odpt.Railway:Keio.Keio",
                                "odpt.Railway:Keio.KeioNew",
                                "odpt.Railway:Odakyu.Odawara",
                                "odpt.Railway:Seibu.Shinjuku",
                                "odpt.Railway:Toei.Oedo",
                                "odpt.Railway:Toei.Shinjuku",
                                "odpt.Railway:TokyoMetro.Marunouchi"
                        ))
                .build());
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

        assertThat(trainTypes).extracting(t -> t.getSameAs()).containsExactly(
                "odpt.TrainType:JR-East.Rapid", "odpt.TrainType:TokyoMetro.SemiExpress");

        assertThat(trainTypes).contains(TrainType.builder()
                .id("urn:ucode:_00001C0000000000000100000320572A")
                .date(LocalDateTime.of(2019,4,25,16,0,0,0))
                .title("快速")
                .sameAs("odpt.TrainType:JR-East.Rapid")
                .operator("odpt.Operator:JR-East")
                .trainTypeTitles(Map.of("en", "Rapid","ja", "快速"))
                .build());
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
        assertThat(trains).extracting(t -> t.getSameAs())
                .containsExactly("odpt.Train:JR-East.SobuRapid.2296F", "odpt.Train:JR-East.SobuRapid.2054M");

        assertThat(trains).contains(Train.builder()
                        .id("urn:uuid:db9becf7-5190-4a3d-8130-75c007e5b9e8")
                        .date(LocalDateTime.of(2021,8,14,22,27,13))
                        .valid(LocalDateTime.of(2021,8,14,22,32,13))
                        .delay(0)
                        .sameAs("odpt.Train:JR-East.SobuRapid.2054M")
                        .railway("odpt.Railway:JR-East.SobuRapid")
                        .operator("odpt.Operator:JR-East")
                        .toStation("odpt.Station:JR-East.SobuRapid.Ichikawa")
                        .trainType("odpt.TrainType:JR-East.LimitedExpress")
                        .fromStation("odpt.Station:JR-East.SobuRapid.Funabashi")
                        .trainNumber("2054M")
                        .railDirection("odpt.RailDirection:Inbound")
                        .carComposition(12)
                        .destinationStations(List.of("odpt.Station:JR-East.Yokosuka.Ofuna"))
                .build());
    }

    @Test
    public void getTrainSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        String railway = "odpt.Railway:JR-East.SobuRapid";

        server.expect(requestTo("http://localhost:8081/odpt:Train?"
                        + "acl:consumerKey=TEST-KEY"
                        + "&odpt:railway=" + railway))
                .andRespond(withSuccess(trainsFile, MediaType.APPLICATION_JSON));

        List<Train> trains = odptApiClient.getTrain(railway);
        assertEquals(2, trains.size());
    }

    @Test
    public void getRailDirectionSuccess() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:RailDirection.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(railDirectionFile, MediaType.APPLICATION_JSON));

        List<RailDirection> railDirections = odptApiClient.getRailDirections();

        assertThat(railDirections).extracting(d -> d.getSameAs())
                .containsExactly("odpt.RailDirection:Inbound", "odpt.RailDirection:Outbound");

        assertThat(railDirections).contains(RailDirection.builder()
                .id("urn:ucode:_00001C0000000000000100000320030B")
                .date(LocalDateTime.of(2019,4,25, 14,0,0))
                .title("上り")
                .sameAs("odpt.RailDirection:Inbound")
                .railDirectionTitles(Map.of("en", "Inbound", "ja", "上り"))
                .build());
    }
}