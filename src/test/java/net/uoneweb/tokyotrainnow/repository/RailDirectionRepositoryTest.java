package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RailDirectionRepositoryTest {

    @Autowired
    RailDirectionRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();

        RailDirection outbound = RailDirection.builder()
                .id("urn:ucode:_00001C0000000000000100000320030D")
                .date(LocalDateTime.of(2019, 04, 25, 14, 00, 00 ))
                .title("下り")
                .sameAs("odpt.RailDirection:Outbound")
                .railDirectionTitles(Map.of("en", "Outbound","ja", "下り"))
                .build();

        repository.add("odpt.RailDirection:Inbound", outbound);
    }

    @Test
    public void findSuccess() {
        RailDirection railDirection = repository.find("odpt.RailDirection:Inbound");
        assertThat(railDirection.getTitle()).isEqualTo("下り");
    }
}
