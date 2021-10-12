package net.uoneweb.tokyotrainnow.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity(name = "metadata")
@Data
@Builder
public class MetaData {
    @Id
    @Builder.Default
    private Long id = 1L;

    private LocalDateTime operatorsUpdateTime;

    private LocalDateTime railwaysUpdateTime;

    private LocalDateTime railDirectionsUpdateTime;

    private LocalDateTime stationsUpdateTime;

    private LocalDateTime trainTypesUpdateTime;
}
