package net.uoneweb.tokyotrainnow.controller;

import net.uoneweb.tokyotrainnow.CurrentRailwayBuilder;
import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SectionsTest {
    @Test
    public void constructor_WithLang_Constructed() {
        final Sections sections = new Sections("ja");
        assertThat(sections).isNotNull();
    }

    @Test
    public void add_ReturnsNewInstance() {
        final Sections sections = new Sections("ja");
        final Sections actual = sections.add(TestDataBuilder.sobuRapidTokyo());
        assertThat(actual).isNotEqualTo(sections);
    }

    @Test
    public void add_OnEmpty_StationIsAdded() {
        final Sections sections = new Sections("ja");
        final Sections actual = sections.add(TestDataBuilder.sobuRapidTokyo());

        assertThat(actual.asList()).containsExactly(
                CurrentRailwayBuilder.tokyo(List.of())
        );
    }

    @Test
    public void add_StationExists_LineAndStationAreAdded() {
        final Sections sections = new Sections("ja");
        final Sections actual = sections.add(TestDataBuilder.sobuRapidTokyo())
                        .add(TestDataBuilder.sobuRapidInage());

        assertThat(actual.asList()).containsExactly(
                CurrentRailwayBuilder.tokyo(List.of()),
                CurrentRailwayBuilder.line(List.of()),
                CurrentRailwayBuilder.inage(List.of())
        );
    }

    @Test
    public void add_AddedAtCorrectSection() {
        final Sections sections = new Sections("ja")
                .add(TestDataBuilder.sobuRapidTokyo())
                .add(TestDataBuilder.sobuRapidInage());

        final Sections actual = sections.add(CurrentRailwayBuilder.t2115f());
        //assertThat(sections.asList().get(0).getTrains().size()).isEqualTo(0); // TODO
        assertThat(actual.asList().get(0).getTrains().size()).isGreaterThan(0);
    }

    @ParameterizedTest
    @CsvSource({
            "odpt.Station:JR-East.SobuRapid.Tokyo, , true, 0",
            "odpt.Station:JR-East.SobuRapid.Tokyo, odpt.Station:JR-East.SobuRapid.Inage, true, 1",
            "odpt.Station:JR-East.SobuRapid.Inage, , true, 2",
            "odpt.Station:JR-East.SobuRapid.Chiba, , true, 4",
            "odpt.Station:JR-East.SobuRapid.Chiba, , false, 4",
            "odpt.Station:JR-East.SobuRapid.Chiba, odpt.Station:JR-East.SobuRapid.Inage , false, 3",
            "odpt.Station:JR-East.SobuRapid.Tokyo, , false, 0",
    })
    public void findIndex_ReturnsCorrectIndex(final String from, final String to, final boolean ascending, final int expected) {
        Sections sections = new Sections("ja")
                .add(TestDataBuilder.sobuRapidTokyo())
                .add(TestDataBuilder.sobuRapidInage())
                .add(TestDataBuilder.sobuRapidChiba());

        final String fixedTo;
        if (!StringUtils.hasText(to)) {
            fixedTo = Station.EMPTY.getSameAs();
        } else {
            fixedTo = to;
        }

        int index = sections.findIndex(from, fixedTo, ascending);

        assertThat(index).isEqualTo(expected);
    }

}
