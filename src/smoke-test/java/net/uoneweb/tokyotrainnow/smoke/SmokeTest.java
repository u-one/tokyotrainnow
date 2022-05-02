package net.uoneweb.tokyotrainnow.smoke;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Tag("smoke-test")
    @Test
    void smokeTest() {

        String response = restTemplate.getForObject("/api/current-railway/odpt.Railway:Toei.Asakusa", String.class);
        assertEquals("", response);
    }
}
