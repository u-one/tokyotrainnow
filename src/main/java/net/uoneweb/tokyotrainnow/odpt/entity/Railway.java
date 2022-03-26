package net.uoneweb.tokyotrainnow.odpt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity; // GCP
import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
/*
for GCP
@Entity(name = "railways")
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonldResource
@JsonldType("odpt:Railway")
public class Railway {
    @JsonldId
    private String id;

    @JsonProperty("dc:date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime date;

    @JsonProperty("dc:title")
    private String title;

    //@Id
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

    public static final Converter<Railway.Station,String> RAILWAYSTATION_STRING_CONVERTER =
            new Converter<Railway.Station, String>() {
                ObjectMapper mapper = new ObjectMapper();
                @Override
                public String convert(Railway.Station station) {
                    String temp = null;
                    try {
                        temp = mapper.writeValueAsString(station);
                    } catch (JsonProcessingException e) {
                        log.error(e.toString());
                    }
                    return temp;
                }
            };
    public static final Converter<String, Railway.Station> STRING_RAILWAYSTATION_CONVERTER =
            new Converter<String, Railway.Station>() {
                ObjectMapper mapper = new ObjectMapper();
                @Override
                public Railway.Station convert(String string) {
                    try {
                        return mapper.readValue(string, Station.class);
                    } catch (JsonProcessingException e) {
                        log.error(e.toString());
                    }
                    return null;
                }
            };
}