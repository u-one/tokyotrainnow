package net.uoneweb.tokyotrainnow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonldResource
@JsonldType("odpt:Station")
public class Station {
    @JsonldId
    private String id;

    @JsonProperty("dc:title")
    private String title;

    @JsonProperty("owl:sameAs")
    private String sameAs;

    @JsonProperty("odpt:railway")
    private String railway;

    @JsonProperty("odpt:operator")
    private String operator;

    @JsonProperty("odpt:stationTitle")
    private Map<String, String> stationTitle;

    @JsonProperty("odpt:connectingRailway")
    private List<String> connectingRailways;

    @JsonProperty("geo:lat")
    private Double geo_lat;

    @JsonProperty("geo:long")
    private Double geo_long;
}
