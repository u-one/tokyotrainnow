package net.uoneweb.tokyotrainnow.client;

import net.uoneweb.tokyotrainnow.entity.Operator;
import net.uoneweb.tokyotrainnow.entity.Train;
import net.uoneweb.tokyotrainnow.entity.TrainType;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DefaultOdptApiClientTest {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DefaultOdptApiClient odptApiClient;

    @Value("classpath:/operators.jsonld")
    Resource operatorsFile;

    @Value("classpath:/__files/traintypes.jsonld")
    Resource trainTypesFile;

    @Value("classpath:/odpt_api_client/trains.jsonld")
    Resource trainsFile;

    @Test
    public void getTrainTypesSucceed() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:TrainType.json?"
                            + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(trainTypesFile, MediaType.APPLICATION_JSON));

        List<TrainType> trainTypes = odptApiClient.getTrainTypes();
    }

    @Test
    public void getOperatorSucceed() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:8081/odpt:Operator.json?"
                        + "acl:consumerKey=TEST-KEY"))
                .andRespond(withSuccess(operatorsFile, MediaType.APPLICATION_JSON));

        List<Operator> operators = odptApiClient.getOperators();
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

        List<Train> trains = odptApiClient.getTrains();
        assertEquals(2, trains.size());
    }
}
