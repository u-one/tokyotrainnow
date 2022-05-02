package net.uoneweb.tokyotrainnow.odpt.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import static org.assertj.core.api.Assertions.assertThat;

public class RailwayTest {
    private static final String STATION_JSON =
            "{\"odpt:index\":1,"
           + "\"odpt:station\":\"odpt.Station:JR-East.SobuRapid.Tokyo\","
           + "\"odpt:stationTitle\":{\"en\":\"Tokyo\",\"ja\":\"東京\"}}";

    @Test
    public void convertToJsonTest() throws Exception {
        final Converter<Railway.Station, String> converter = Railway.RAILWAYSTATION_STRING_CONVERTER;

        final Railway railway = TestDataBuilder.soubuRapid();
        final Railway.Station station = railway.getStationOrder().get(0);

        final String actualJson =  converter.convert(station);
        final String expectedJson = STATION_JSON;

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actual = mapper.readTree(actualJson);
        final JsonNode expected = mapper.readTree(expectedJson);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void convertFromJsonTest() throws Exception {
        final Converter<String, Railway.Station> converter = Railway.STRING_RAILWAYSTATION_CONVERTER;

        final String input = STATION_JSON;
        final Railway.Station actual =  converter.convert(input);

        final Railway railway = TestDataBuilder.soubuRapid();
        final Railway.Station expected = railway.getStationOrder().get(0);

        assertThat(actual).isEqualTo(expected);
    }

}
