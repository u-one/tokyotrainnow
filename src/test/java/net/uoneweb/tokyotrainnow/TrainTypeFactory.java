package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;

import java.time.LocalDateTime;
import java.util.Map;

public class TrainTypeFactory {
    public static TrainType rapid() {
        return TrainType.builder()
                .id("urn:ucode:_00001C0000000000000100000320572A")
                .date(LocalDateTime.of(2019,04,25,16,00,00))
                .title("快速")
                .sameAs("odpt.TrainType:JR-East.Rapid")
                .operator("odpt.Operator:JR-East")
                .trainTypeTitles(Map.of("en", "Rapid", "ja", "快速"))
                .build();
    }
}
