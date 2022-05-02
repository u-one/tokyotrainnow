package net.uoneweb.tokyotrainnow.config;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SiteConfigTest {
    private SiteConfig siteConfig = new SiteConfig();

    @Test
    void testSupportedRailways() {
        final Map<Operator, List<Railway>> supported = siteConfig.supportedRailways();
        assertThat(supported.keySet())
                .extracting(o -> o.getTitle())
                .containsExactlyInAnyOrder("都営地下鉄", "都電");

        final List<Railway> toeiSubways = supported.entrySet().stream()
                .filter(e -> e.getKey().getTitle().equals("都営地下鉄"))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());

        assertThat(toeiSubways).contains(
                Railway.builder().title("都営新宿線").sameAs("odpt.Railway:Toei.Shinjuku").build()
        );
        assertThat(toeiSubways.size()).isEqualTo(4);

        assertThat(supported.entrySet().stream()
                .filter(e -> e.getKey().getTitle().equals("都電"))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList())
                .size()).isEqualTo(1);
    }

    @Test
    void testUnsupportedRailways() {
        final Map<Operator, List<Railway>> unsupported = siteConfig.unsupportedRailways();

        assertThat(unsupported.keySet())
                .extracting(o -> o.getTitle())
                .containsExactlyInAnyOrder("JR東日本", "東京メトロ");

        assertThat(unsupported.entrySet().stream()
                .filter(e -> e.getKey().getTitle().equals("JR東日本"))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList())
                .size()).isEqualTo(24);

        assertThat(unsupported.entrySet().stream()
                .filter(e -> e.getKey().getTitle().equals("東京メトロ"))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList())
                .size()).isEqualTo(10);
    }
}