package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.entity.TrainType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainTypeRepositoryTest {

    @Mock
    private OdptApiClient apiClient;

    @InjectMocks
    private TrainTypeRepository trainTypeRepository;

    @Test
    public void findAllSuccess() {
        trainTypes().stream().forEach(t -> trainTypeRepository.add(t.getSameAs(), t));

        List<TrainType> trainTypes = trainTypeRepository.findAll();

        List<String> actual = trainTypes.stream().map(t -> t.getTitle()).collect(Collectors.toList());
        Collections.sort(actual);

        assertIterableEquals(List.of("快速", "準急", "特急"), actual);
    }

    @Test
    public void findByOperatorSuccess() {
        trainTypes().stream().forEach(t -> trainTypeRepository.add(t.getSameAs(), t));

        List<TrainType> trainTypes = trainTypeRepository.findAllByOperator("odpt.Operator:JR-East");

        List<String> actual = trainTypes.stream().map(t -> t.getTitle()).collect(Collectors.toList());
        Collections.sort(actual);

        assertIterableEquals(List.of("快速", "特急"), actual);
    }

    private static List<TrainType> trainTypes() {
        return List.of(
                TrainType.builder()
                        .id("urn:ucode:_00001C0000000000000100000320572A")
                        //.date(LocalDateTime.of(2019, 4, 25, 16, 0, 0))
                        .date(LocalDateTime.parse("2019-04-25T16:00:00+09:00"))
                        .title("快速")
                        .sameAs("odpt.TrainType:JR-East.Rapid")
                        .operator("odpt.Operator:JR-East")
                        .trainTypeTitle(
                                Map.of(
                                        "en", "Rapid",
                                        "ja", "快速"
                                )
                        )
                        .build(),
                TrainType.builder()
                        .id("urn:ucode:_00001C00000000000001000003205726")
                        //.date(LocalDateTime.of(2019, 4, 25, 16, 0, 0))
                        .date(LocalDateTime.parse("2019-04-25T16:00:00+09:00"))
                        .title("特急")
                        .sameAs("odpt.TrainType:JR-East.LimitedExpress")
                        .operator("odpt.Operator:JR-East")
                        .trainTypeTitle(
                                Map.of(
                                        "en", "Limited Express",
                                        "ja", "特急"
                                )
                        )
                        .build(),
                TrainType.builder()
                        .id("urn:ucode:_00001C000000000000010000030E8B79")
                        //.date(LocalDateTime.of(2019, 4, 25, 16, 0, 0))
                        .date(LocalDateTime.parse("2019-04-25T16:00:00+09:00"))
                        .title("準急")
                        .sameAs("odpt.TrainType:TokyoMetro.SemiExpress")
                        .operator("odpt.Operator:TokyoMetro")
                        .trainTypeTitle(
                                Map.of(
                                        "en", "Semi Express",
                                        "ja", "準急"
                                )
                        )
                        .build()
                );

    }
}
