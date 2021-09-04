package net.uoneweb.tokyotrainnow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocalSearchResponse {
    @JsonProperty("ResultInfo")
    private ResultInfo resultInfo;

    @Data
    public static class ResultInfo {
        @JsonProperty("Count")
        private int count;

        @JsonProperty("Total")
        private int total;
    }
}
