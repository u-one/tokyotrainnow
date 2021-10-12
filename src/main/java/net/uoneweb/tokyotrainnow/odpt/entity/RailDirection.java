package net.uoneweb.tokyotrainnow.odpt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;

import java.time.LocalDateTime;
import java.util.Map;

@Entity(name = "rail_directions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonldResource
@JsonldType("odpt:RailDirection")
public class RailDirection {
    @JsonldId
    private String id;

    @JsonProperty("dc:date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime date;

    @JsonProperty("dc:title")
    private String title;

    @JsonProperty("owl:sameAs")
    private String sameAs;

    @JsonProperty("odpt:operator")
    private String operator;

    @JsonProperty("odpt:railDirectionTitle")
    private Map<String, String> railDirectionTitles;
}
