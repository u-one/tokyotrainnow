package net.uoneweb.tokyotrainnow.entity;

import net.uoneweb.tokyotrainnow.CurrentRailwayBuilder;
import net.uoneweb.tokyotrainnow.RailDirectionFactory;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.controller.Trains;
import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentRailwayTest {
    @Mock
    private Clock clock;

    @Mock
    private Trains trains;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));
    }

    @Test
    public void constructor_LangIsJa_Constructs() {
        when(clock.instant()).thenReturn(Instant.parse("2021-03-27T12:00:00.000Z"));
        when(clock.withZone(ZoneId.of("Asia/Tokyo"))).thenReturn(clock);

        final Operator operator = TestDataBuilder.jrEast();
        final Railway railway = TestDataBuilder.soubuRapid();
        final RailDirection ascending = RailDirectionFactory.inbound();
        final RailDirection descending = RailDirectionFactory.outbound();
        final List<Station> stations = List.of(
                TestDataBuilder.sobuRapidTokyo(),
                TestDataBuilder.sobuRapidInage(),
                TestDataBuilder.sobuRapidChiba());
        final Trains trains = new Trains().add(CurrentRailwayBuilder.t2115f());
        final MetaData metadata = MetaData.builder().build();

        final CurrentRailway actual = new CurrentRailway(operator, railway, ascending, descending
        , stations, trains, metadata, clock, "ja");

        final CurrentRailway expected = CurrentRailwayBuilder.subuRapid();
        expected.setOperatorUpdateTime(null);
        expected.setRailwayUpdateTime(null);
        expected.setTrainTypeUpdateTime(null);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void calculateValidLimit_ReturnsInSec() {
        when(clock.instant()).thenReturn(Instant.parse("2021-10-01T11:59:00.000Z"));
        when(clock.withZone(ZoneId.of("Asia/Tokyo"))).thenReturn(clock);

        when(trains.validLimit()).thenReturn(LocalDateTime.of(2021,10,01,12,00,00));

        assertThat(CurrentRailway.calculateValidLimit(trains, clock)).isEqualTo(60);
    }

    @Test
    public void validLimit_OnNull_Returns300sec() {
        when(clock.instant()).thenReturn(Instant.parse("2021-10-01T11:59:00.000Z"));
        when(clock.withZone(ZoneId.of("Asia/Tokyo"))).thenReturn(clock);
        when(trains.validLimit()).thenReturn(null);

        assertThat(CurrentRailway.calculateValidLimit(trains, clock)).isEqualTo(300);
    }
}
