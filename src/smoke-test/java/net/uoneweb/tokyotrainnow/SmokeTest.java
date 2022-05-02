package net.uoneweb.tokyotrainnow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureStubRunner(
        ids = {"net.uoneweb:tokyotrainnow:stubs"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class SmokeTest {
    @Autowired
    TestRestTemplate restTemplate;

    @StubRunnerPort("tokyotrainnow")
    int apiPort;

    @BeforeEach
    public void setupPort() {

    }

    @Tag("smoke")
    @Test
    void smokeTest() {
        restTemplate.getForEntity("/api/currentRailway", String.class);
    }
}
