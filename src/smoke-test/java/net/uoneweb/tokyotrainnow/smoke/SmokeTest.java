package net.uoneweb.tokyotrainnow.smoke;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Tag("smoke-test")
    @Test
    void smokeTest() throws Exception {

        final String jsonResponse = restTemplate.getForObject("/api/current-railway/odpt.Railway:Toei.Asakusa", String.class);

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode root = mapper.readTree(jsonResponse);

        assertThat(root.get("title").asText()).isEqualTo("浅草線");
        assertThat(root.get("lineCode").asText()).isEqualTo("A");
        assertThat(root.get("color").asText()).isEqualTo("#FF535F");
        assertThat(root.get("operator").asText()).isEqualTo("東京都交通局");
        assertThat(root.get("ascendingTitle").asText()).isEqualTo("北行");
        assertThat(root.get("descendingTitle").asText()).isEqualTo("南行");
        assertThat(root.get("ascendingTitle").asText()).isEqualTo("北行");

        assertThat(root.get("sections")).extracting(obj -> obj.get("title").asText())
                .containsSequence("西馬込", "|", "馬込", "|", "中延", "|", "戸越", "|", "五反田", "|", "高輪台",
                        "|", "泉岳寺", "|", "三田", "|", "大門", "|", "新橋", "|", "東銀座",
                        "|", "宝町", "|", "日本橋", "|", "人形町", "|", "東日本橋", "|", "浅草橋",
                        "|", "蔵前", "|", "浅草", "|", "本所吾妻橋", "|", "押上");

        // "20YY-MM-DDThh:mm:ss.uuuuuuu" or "20YY-MM-DDThh:mm:ss"
        final Pattern datePattern = Pattern.compile("20\\d{2}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(.\\d{6,7})?");
        assertThat(root.get("operatorUpdateTime").asText()).matches(datePattern);
        assertThat(root.get("railwayUpdateTime").asText()).matches(datePattern);
        assertThat(root.get("trainTypeUpdateTime").asText()).matches(datePattern);
        assertThat(root.get("trainDate").asText()).matches(datePattern);
        assertThat(root.get("validSeconds").asInt()).isGreaterThan(0);
    }
}
