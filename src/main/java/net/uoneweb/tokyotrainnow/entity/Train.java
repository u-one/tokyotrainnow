package net.uoneweb.tokyotrainnow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonldResource
@JsonldType("odpt:Train")
public class Train {
    @JsonldId
    private String id;

    @JsonProperty("dc:date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime date;

    @JsonProperty("dct:valid")
    private String valid;

    @JsonProperty("odpt:operator")
    private String operator;

    @JsonProperty("odpt:railway")
    private String railway;

    @JsonProperty("odpt:railDirection")
    private String railDirection;

    @JsonProperty("odpt:trainNumber")
    private String trainNumber;

    @JsonProperty("odpt:trainType")
    private String trainType;

    @JsonProperty("odpt:fromStation")
    private String fromStation;

    @JsonProperty("odpt:toStation")
    private String toStation;

    @JsonProperty("odpt:destinationStation")
    private List<String> destinationStation;

    @JsonProperty("odpt:index")
    private int index;

    @JsonProperty("odpt:delay")
    private int delay;

    @JsonProperty("odpt:carComposition")
    private int carComposition;
}
