package net.uoneweb.tokyotrainnow.odpt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonldResource
@JsonldType("odpt:Railway")
public class Railway {
    @JsonldId
    private String id;

    @JsonProperty("dc:title")
    private String title;

    @JsonProperty("owl:sameAs")
    private String sameAs;

    @JsonProperty("odpt:color")
    private String color;

    @JsonProperty("odpt:lineCode")
    private String lineCode;

    @JsonProperty("odpt:operator")
    private String operator;

    @JsonProperty("odpt:railwayTitle")
    private Map<String, String> railwayTitles;

    @JsonProperty("odpt:stationOrder")
    private List<Station> stationOrder;

    @JsonProperty("odpt:ascendingRailDirection")
    private String ascendingRailDirection;

    @JsonProperty("odpt:descendingRailDirection")
    private String descendingRailDirection;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonldResource
    public static class Station {
        @JsonProperty("odpt:index")
        private int index;

        @JsonProperty("odpt:station")
        private String station;

        @JsonProperty("odpt:stationTitle")
        private Map<String, String> stationTitles;
    }
}