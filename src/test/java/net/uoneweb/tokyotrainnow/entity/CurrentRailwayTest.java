package net.uoneweb.tokyotrainnow.entity;

import net.uoneweb.tokyotrainnow.CurrentRailwayBuilder;
import net.uoneweb.tokyotrainnow.RailDirectionFactory;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.controller.TrainOnRail;
import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrentRailwayTest {

    @Test
    public void constructor_LangIsJa_Constructs() {
        final Operator operator = TestDataBuilder.jrEast();
        final Railway railway = TestDataBuilder.soubuRapid();
        final RailDirection ascending = RailDirectionFactory.inbound();
        final RailDirection descending = RailDirectionFactory.outbound();
        final List<Station> stations = List.of(
                TestDataBuilder.sobuRapidTokyo(),
                TestDataBuilder.sobuRapidInage(),
                TestDataBuilder.sobuRapidChiba());
        final List<TrainOnRail> trains = List.of(CurrentRailwayBuilder.t2115f());

        final CurrentRailway actual = new CurrentRailway(operator, railway, ascending, descending
        , stations, trains, "ja");

        final CurrentRailway expected = CurrentRailwayBuilder.subuRapid();
        expected.setOperatorUpdateTime(null);
        expected.setRailwayUpdateTime(null);
        expected.setTrainTypeUpdateTime(null);
        expected.setTrainDate(null);
        expected.setValidSeconds(0);

        assertThat(actual).isEqualTo(expected);
    }


}
