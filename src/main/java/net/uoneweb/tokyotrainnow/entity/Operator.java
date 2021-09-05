package net.uoneweb.tokyotrainnow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
@JsonldResource
@JsonldType("odpt:Operator")
public class Operator {
    @JsonldId
    private String id;

    @JsonProperty("dc:title")
    private String title;

    @JsonProperty("owl:sameAs")
    private String sameAs;

    @JsonProperty("odpt:operatorTitle")
    private Map<String, String> operatorTitles;
}