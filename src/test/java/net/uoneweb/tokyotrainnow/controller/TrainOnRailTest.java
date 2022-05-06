package net.uoneweb.tokyotrainnow.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.uoneweb.tokyotrainnow.CurrentRailwayBuilder;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainOnRailTest {

    @Test
    public void getTrainType_ReturnsTrainType() {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();

        assertThat(train.getTrainType()).isEqualTo("快速");
    }

    @Test
    public void getDestination_OnEmpty_ReturnsHyphen() {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();

        train.setDestinations(List.of());

        assertThat(train.getDestination()).isEqualTo("-");
    }

    @Test
    public void getDestination_SingleDestination_ReturnsTitle() {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();

        assertThat(train.getDestination()).isEqualTo("千葉");
    }

    @Test
    public void getDestination_MultiDestinations_ReturnsTitlesConnectedWithPoint() {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();
        train.setDestinations(List.of(TestDataBuilder.sobuRapidChiba(), TestDataBuilder.uchiboKimitsu()));

        assertThat(train.getDestination()).isEqualTo("千葉・君津");
    }

    @Test
    public void getFrom_ReturnsTitle() {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();

        assertThat(train.getFrom()).isEqualTo("東京");
    }

    @Test
    public void getTo_ReturnsTitle() {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();
        train.setTo(TestDataBuilder.sobuRapidInage());

        assertThat(train.getTo()).isEqualTo("稲毛");
    }

    @Test
    public void json_CorrectFormat() throws Exception {
        final TrainOnRail train = CurrentRailwayBuilder.t2115f();

        final ObjectMapper mapper = new ObjectMapper();
        final String actualJson = mapper.writeValueAsString(train);

        final String expectedJson =
                "{\"destination\": \"千葉\"," +
                "\"trainNumber\": \"2115F\"," +
                "\"trainType\": \"快速\"," +
                "\"carComposition\": 15," +
                "\"delay\": 0," +
                "\"ascending\": false}";

        final JsonNode actual = mapper.readTree(actualJson);
        final JsonNode expected = mapper.readTree(expectedJson);

        assertThat(actual).isEqualTo(expected);
    }
}
