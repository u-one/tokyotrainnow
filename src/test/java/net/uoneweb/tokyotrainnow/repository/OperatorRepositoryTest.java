package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.entity.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OperatorRepositoryTest {

    @Autowired
    private OperatorRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();

        Operator jrEast = Operator.builder()
                .id("urn:ucode:_00001C000000000000010000030E6606")
                .title("JR東日本").sameAs("odpt.Operator:JR-East")
                .operatorTitles(Map.of("en", "JR East","ja", "JR東日本"))
                .build();
        Operator tokyoMetro = Operator.builder()
                .id("urn:ucode:_00001C000000000000010000030E6600")
                .title("東京メトロ").sameAs("odpt.Operator:TokyoMetro")
                .operatorTitles(Map.of("en", "Tokyo Metro","ja", "東京メトロ"))
                .build();

        repository.add("odpt.Operator:JR-East",  jrEast);
        repository.add("odpt.Operator:TokyoMetro", tokyoMetro);
    }

    @Test
    public void findAllSuccess() {
        List<Operator> operators = repository.findAll();
        List<String> titles = operators.stream().map(o -> o.getTitle()).sorted().collect(Collectors.toList());
        assertIterableEquals(List.of("JR東日本", "東京メトロ"), titles);
    }

    @Test
    public void findSuccess() {
        Operator operator = repository.findByOperatorId("odpt.Operator:JR-East");
        assertEquals("JR東日本", operator.getTitle());
    }
}
