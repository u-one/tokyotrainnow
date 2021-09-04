package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.Railway;
import net.uoneweb.tokyotrainnow.entity.Station;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/geojson")
public class TrainApiController {
    @Autowired
    private TrainService trainService;

    @GetMapping(path = "stations")
    public GeoJsonResource getStations() {
        String railwayId = "odpt.Railway:JR-East.SobuRapid";
        Railway railway = trainService.getRailway(railwayId);

        List<GeoJsonResource.GeoJsonFeature> features = new ArrayList<>();

        for (Railway.Station railwayStation : railway.getStationOrder()) {
            String stationId = railwayStation.getStation();
            Optional<Station> stationOpt = trainService.getStation(stationId);
            if (stationOpt.isPresent()) {
                Station station = stationOpt.get();
                log.info(station.toString());

                features.add(GeoJsonResource.GeoJsonFeature.builder()
                        .type("Feature")
                        .geometry(GeoJsonResource.GeoJsonGeometry.builder()
                                .type("Point")
                                .coordinates(List.of(station.getGeo_long(), station.getGeo_lat()))
                                .build())
                        .properties(Map.of("title", station.getTitle()))
                        .build());
            }
        }

        GeoJsonResource geoJsonResource = GeoJsonResource.builder()
                .type("FeatureCollection")
                .features(features)
                .build();

        return geoJsonResource;
    }


        @GetMapping(path = "sample")
    public GeoJsonResource getGeoJson() {


        GeoJsonResource.GeoJsonGeometry geometry = GeoJsonResource.GeoJsonGeometry.builder()
                .type("Point")
                .coordinates(List.of(139.5196, 35.6180))
                .build();

        GeoJsonResource.GeoJsonFeature feature =
                GeoJsonResource.GeoJsonFeature.builder()
                        .type("Feature")
                        .geometry(geometry)
                        .properties(Map.of("title", "Point 1"))
                        .build();

        GeoJsonResource geoJsonResource = GeoJsonResource.builder()
                .type("FeatureCollection")
                .features(List.of(feature, GeoJsonResource.GeoJsonFeature.builder()
                        .type("Feature")
                        .geometry(GeoJsonResource.GeoJsonGeometry.builder()
                                .type("Point")
                                .coordinates(List.of(139.5197, 35.6190))
                                .build())
                        .properties(Map.of("title", "Point 2"))
                        .build()))
                .build();

        return geoJsonResource;
    }
}
