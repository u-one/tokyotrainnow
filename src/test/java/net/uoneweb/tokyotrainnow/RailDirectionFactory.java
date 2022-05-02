package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;

import java.time.LocalDateTime;
import java.util.Map;

public class RailDirectionFactory {
    public static RailDirection inbound() {
        return RailDirection.builder()
                .id("urn:ucode:_00001C0000000000000100000320030B")
                .date(LocalDateTime.of(2019,04,25,14,00,00))
                .title("上り")
                .sameAs("odpt.RailDirection:Inbound")
                .railDirectionTitles(Map.of("en", "Inbound", "ja", "上り"))
                .build();
    }

    public static RailDirection outbound() {
        return RailDirection.builder()
                .id("urn:ucode:_00001C0000000000000100000320030D")
                .date(LocalDateTime.of(2019,04,25,14,00,00))
                .title("下り")
                .sameAs("odpt.RailDirection:Outbound")
                .railDirectionTitles(Map.of("en", "Outbound", "ja", "下り"))
                .build();
    }
}
