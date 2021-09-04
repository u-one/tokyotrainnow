package net.uoneweb.tokyotrainnow;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegTest {

    WireMockServer wireMockServer = new WireMockServer(8081);

    @Autowired
    TestRestTemplate testRestTemplate;

    @AfterEach
    public void afterEach() {
        wireMockServer.resetAll();
    }

    @Test
    public void normal_test() {
        wireMockServer.start();
        configureFor("localhost", 8081);

        stubFor(get(urlMatching("/odpt:Train\\?acl:consumerKey=TEST-KEY&odpt:operator=odpt.Operator:JR-East&odpt:railway=odpt.Railway:JR-East.SobuRapid"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("train_soburapid.jsonld")
                        .withHeader("Content-Type", "application/json")
                ));

        stubFor(get(urlMatching("/odpt:Station.json\\?acl:consumerKey=TEST-KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("station_test.json")
                        .withHeader("Content-Type", "application/json")
                ));

        ResponseEntity<String> response = testRestTemplate.getForEntity("/train", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
