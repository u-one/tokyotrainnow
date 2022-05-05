package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Sections {
    private final String lang;
    private final List<CurrentRailway.Section> sections;

    public Sections(String lang) {
        this.lang = lang;
        this.sections = new ArrayList<>();
    }

    private Sections(String lang, List<CurrentRailway.Section> sections) {
        this.lang = lang;
        this.sections = sections;
    }

    public Sections add(Station station) {
        final CurrentRailway.Section section = CurrentRailway.EStation.builder()
                .title(station.getStationTitle().get(lang))
                .stationId(station.getSameAs())
                .stationCode(station.getStationCode()).build();

        final List<CurrentRailway.Section> sections = new ArrayList<>(this.sections);
        if (!sections.isEmpty()) {
            sections.add(CurrentRailway.Line.builder()
                    .title("|")
                    .build());
        }
        sections.add(section);
        return new Sections(lang, sections);
    }

    public Sections add(final TrainOnRail train) {
        final String from = train.from.getSameAs();
        final String to = train.to.getSameAs();
        int index = resolveIndex(from, to, train.isAscending());
        final List<CurrentRailway.Section> sections = new ArrayList<>(this.sections);
        final CurrentRailway.Section section  = sections.get(index);
        section.addTrain(train);
        return new Sections(lang, sections);
    }

    int resolveIndex(final String from, final String to, final boolean ascending) {
        int lineIndex = findIndex(from);
        if (!isStationEmpty(to)) {
            // index between from and to
            if (ascending) {
                lineIndex = lineIndex + 1;
            } else {
                lineIndex = lineIndex - 1;
            }
        }

        if (!isValidIndex(lineIndex)) {
            throw new RuntimeException(String.format("invalid index: ", lineIndex, from, to, sections));
        }
        return lineIndex;
    }

    private int findIndex(final String stationId) {
        for (int i = 0; i < sections.size(); i++) {
            CurrentRailway.Section section = sections.get(i);
            if (section.getStationId().equals(stationId)) {
                return i;
            }
        }
        throw new RuntimeException(String.format("not found index on railway: ", stationId, sections));
    }

    private static boolean isStationEmpty(final String stationId) {
        if (!StringUtils.hasText(stationId)) {
            return true;
        }
        return Station.EMPTY.getSameAs().equals(stationId);
    }

    private boolean isValidIndex(final int index) {
        if (index >= sections.size()) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        return true;
    }

    public List<CurrentRailway.Section> asList() {
        return Collections.unmodifiableList(sections);
    }
}
