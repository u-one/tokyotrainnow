package net.uoneweb.tokyotrainnow.controller;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class GeoJsonResource {

    private String type;

    private List<GeoJsonFeature> features;

    @Data
    @Builder
    public static class GeoJsonFeature {
        private String type;

        private GeoJsonGeometry geometry;

        private Map<String, String> properties;
    }

    @Data
    @Builder
    public static class GeoJsonGeometry {
        private String type;

        private List<Double> coordinates;
    }
}
