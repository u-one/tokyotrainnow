package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.odpt.entity.Station;

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

    public Sections add(TrainOnRail train) {
        String from = train.from.getSameAs();
        String to = train.to.getSameAs();
        int index = findIndex(from, to, train.isAscending());
        final List<CurrentRailway.Section> sections = new ArrayList<>(this.sections);
        final CurrentRailway.Section section  = sections.get(index);
        section.addTrain(train);
        return new Sections(lang, sections);
    }

    int findIndex(String from, String to, boolean ascending) {
        if (ascending) {
            for (int i = 0; i < sections.size(); i++) {
                CurrentRailway.Section section = sections.get(i);
                if (!section.getStationId().equals(from)) {
                    continue;
                }
                if (Station.EMPTY.getSameAs().equals(to)) {
                    return i;
                }
                int lineIndex = i + 1;
                if (lineIndex >= sections.size()) {
                    log.error("Section検出エラー(不正なインデックス)", lineIndex, from, to, sections);
                }
                return lineIndex;
            }
        } else {
            for (int i = sections.size() - 1; i >= 0; i--) {
                CurrentRailway.Section section = sections.get(i);
                if (!section.getStationId().equals(from)) {
                    continue;
                }
                if (Station.EMPTY.getSameAs().equals(to)) {
                    return i;
                }
                int lineIndex = i - 1;
                if (lineIndex < 0) {
                    log.error("Section検出エラー(不正なインデックス)", lineIndex, from, to, sections);
                }
                return lineIndex;
            }
        }
        log.error("Section検出エラー(一致なし)", from, to, sections);
        return 0;
    }

    public List<CurrentRailway.Section> asList() {
        return Collections.unmodifiableList(sections);
    }
}
