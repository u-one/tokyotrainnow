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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonldResource
@JsonldType("odpt:Station")
public class Station {
    @JsonldId
    private String id;

    @JsonProperty("dc:date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime date;

    @JsonProperty("dc:title")
    private String title;

    @JsonProperty("owl:sameAs")
    private String sameAs;

    @JsonProperty("odpt:railway")
    private String railway;

    @JsonProperty("odpt:operator")
    private String operator;

    @JsonProperty("odpt:stationCode")
    private String stationCode;

    @JsonProperty("odpt:stationTitle")
    private Map<String, String> stationTitle;

    @JsonProperty("odpt:passengerSurvey")
    private List<String> passengerSurveys;

    @JsonProperty("odpt:stationTimetable")
    private List<String> stationTimetables;

    @JsonProperty("odpt:connectingRailway")
    private List<String> connectingRailways;

    @JsonProperty("geo:lat")
    private Double geo_lat;

    @JsonProperty("geo:long")
    private Double geo_long;
}