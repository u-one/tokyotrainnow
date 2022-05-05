package net.uoneweb.tokyotrainnow.controller;

import net.uoneweb.tokyotrainnow.CurrentRailwayBuilder;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.TrainTypeFactory;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainsTest {
    @Test
    public void constructor_Constructed() {
        final Trains trains = new Trains();
        assertThat(trains).isNotNull();
    }

    @Test
    public void add_ReturnsNewInstance() {
        final Trains trains = new Trains();
        final Trains actual = trains.add(CurrentRailwayBuilder.t2115f());
        assertThat(actual).isNotEqualTo(trains);
    }

    @Test
    public void add_OnEmpty_TrainIsAdded() {
        final Trains trains = new Trains();
        final Trains actual = trains.add(CurrentRailwayBuilder.t2115f());

        assertThat(actual.asList()).containsExactly(
                CurrentRailwayBuilder.t2115f()
        );
    }

    @Test
    public void lastTrainDateReturnsLatestOne() {
        final Trains trains = new Trains()
                .add(createTrainWithDate("0001F", LocalDateTime.of(2021,10,01,12,00,00)))
                .add(createTrainWithDate("0002F", LocalDateTime.of(2021,10,01,12,00,01)))
                .add(createTrainWithDate("0003F", LocalDateTime.of(2021,10,01,12,00,02)));

        assertThat(trains.lastTrainDate()).isEqualTo(LocalDateTime.of(2021, 10,01, 12,00,02));
    }

    @Test
    public void lastTrainDateMayReturnNull() {
        final Trains trains = new Trains();
        assertThat(trains.lastTrainDate()).isNull();
    }

    @Test
    public void validLimitReturnsNearestOne() {
        final Trains trains = new Trains()
                .add(createTrainWithValid("0001F", LocalDateTime.of(2021,10,01,12,00,00)))
                .add(createTrainWithValid("0002F", LocalDateTime.of(2021,10,01,12,00,01)))
                .add(createTrainWithValid("0003F", LocalDateTime.of(2021,10,01,12,00,02)));

        assertThat(trains.validLimit()).isEqualTo(LocalDateTime.of(2021, 10,01, 12,00,00));
    }

    @Test
    public void validLimitReturnsNull() {
        final Trains trains = new Trains();
        assertThat(trains.validLimit()).isNull();
    }

    private TrainOnRail createTrainWithDate(String trainNumber, LocalDateTime date) {
        return TrainOnRail.builder()
                .trainNumber(trainNumber)
                .trainType(TrainTypeFactory.rapid())
                .from(TestDataBuilder.sobuRapidTokyo())
                .to(Station.EMPTY)
                .destinations(List.of(TestDataBuilder.sobuRapidChiba()))
                .date(date)
                .build();
    }

    private TrainOnRail createTrainWithValid(String trainNumber, LocalDateTime valid) {
        return TrainOnRail.builder()
                .trainNumber(trainNumber)
                .trainType(TrainTypeFactory.rapid())
                .from(TestDataBuilder.sobuRapidTokyo())
                .to(Station.EMPTY)
                .destinations(List.of(TestDataBuilder.sobuRapidChiba()))
                .valid(valid)
                .build();
    }
}
