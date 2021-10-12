package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TrainTypeRepositoryTest {

    @Autowired
    private DefaultTrainTypeRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();

        TrainType local = TrainType.builder()
                .title("普通")
                .sameAs("odpt.TrainType:JR-East.Local")
                .operator("odpt.Operator:JR-East")
                .trainTypeTitles(Map.of("en", "Local","ja", "普通"))
                .build();

        TrainType rapid = TrainType.builder()
                .sameAs("odpt.TrainType:JR-East.Rapid")
                .title("快速")
                .operator("odpt.Operator:JR-East")
                .trainTypeTitles(Map.of("en", "Rapid","ja", "快速"))
                .build();

        TrainType semiexp = TrainType.builder()
                .title("準急")
                .sameAs("odpt.TrainType:TokyoMetro.SemiExpress")
                .operator("odpt.Operator:TokyoMetro")
                .trainTypeTitles(Map.of("en", "Semi Express","ja", "準急"))
                .build();

        repository.save(local);
        repository.save(rapid);
        repository.save(semiexp);
    }

    @Test
    public void findByOperatorIdSuccess() {
        List<TrainType> trainTypes = repository.findByOperatorId("odpt.Operator:JR-East");
        List<String> titles = trainTypes.stream().map(o -> o.getTitle()).sorted().collect(Collectors.toList());
        assertIterableEquals(List.of("快速", "普通"), titles);
    }

    @Test
    public void findByTrainTypeIdSuccess() {
        Optional<TrainType> opt = repository.findById("odpt.TrainType:TokyoMetro.SemiExpress");
        assertThat(opt).hasValueSatisfying(trainType -> {
            assertThat(trainType.getTitle()).isEqualTo("準急");
        });
    }
}
