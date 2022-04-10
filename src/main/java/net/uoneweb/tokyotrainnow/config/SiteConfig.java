package net.uoneweb.tokyotrainnow.config;

import net.uoneweb.tokyotrainnow.odpt.entity.Operator;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SiteConfig {
    public Map<Operator, List<Railway>> supportedRailways() {
        return Map.of(
                operator("都営地下鉄"),
                List.of(
                        railway("都営浅草線","odpt.Railway:Toei.Asakusa"),
                        railway("都営三田線","odpt.Railway:Toei.Mita"),
                        railway("都営大江戸線","odpt.Railway:Toei.Oedo"),
                        railway("都営新宿線","odpt.Railway:Toei.Shinjuku")
                ),
                operator("都電"),
                List.of(railway("荒川線","odpt.Railway:Toei.Arakawa"))
        );
    }

    public Map<Operator, List<Railway>> unsupportedRailways() {
        return Map.of(
                operator("JR東日本"),
                List.of(
                        railway("中央線快速", "odpt.Railway:JR-East.ChuoRapid"),
                        railway("中央・総武線各駅停車", "odpt.Railway:JR-East.ChuoSobuLocal"),
                        railway("八高線", "odpt.Railway:JR-East.Hachiko"),
                        railway("伊東線", "odpt.Railway:JR-East.Ito"),
                        railway("五日市線", "odpt.Railway:JR-East.Itsukaichi"),
                        railway("常磐線", "odpt.Railway:JR-East.Joban"),
                        railway("常磐線各駅停車", "odpt.Railway:JR-East.JobanLocal"),
                        railway("常磐線快速", "odpt.Railway:JR-East.JobanRapid"),
                        railway("川越線", "odpt.Railway:JR-East.Kawagoe"),
                        railway("京浜東北線・根岸線", "odpt.Railway:JR-East.KeihinTohokuNegishi"),
                        railway("京葉線", "odpt.Railway:JR-East.Keiyo"),
                        railway("武蔵野線", "odpt.Railway:JR-East.Musashino"),
                        railway("南武線", "odpt.Railway:JR-East.Nambu"),
                        railway("青梅線", "odpt.Railway:JR-East.Ome"),
                        railway("埼京線・川越線", "odpt.Railway:JR-East.SaikyoKawagoe"),
                        railway("湘南新宿ライン", "odpt.Railway:JR-East.ShonanShinjuku"),
                        railway("総武線快速", "odpt.Railway:JR-East.SobuRapid"),
                        railway("相鉄直通線", "odpt.Railway:JR-East.SotetsuDirect"),
                        railway("高崎線", "odpt.Railway:JR-East.Takasaki"),
                        railway("東海道線", "odpt.Railway:JR-East.Tokaido"),
                        railway("宇都宮線", "odpt.Railway:JR-East.Utsunomiya"),
                        railway("山手線", "odpt.Railway:JR-East.Yamanote"),
                        railway("横浜線", "odpt.Railway:JR-East.Yokohama"),
                        railway("横須賀線", "odpt.Railway:JR-East.Yokosuka")
                ),
                operator("東京メトロ"),
                List.of(
                        railway("千代田線","odpt.Railway:TokyoMetro.Chiyoda" ),
                        railway("副都心線","odpt.Railway:TokyoMetro.Fukutoshin" ),
                        railway("銀座線","odpt.Railway:TokyoMetro.Ginza"),
                        railway("半蔵門線","odpt.Railway:TokyoMetro.Hanzomon" ),
                        railway("日比谷線","odpt.Railway:TokyoMetro.Hibiya"),
                        railway("丸ノ内線","odpt.Railway:TokyoMetro.Marunouchi" ),
                        railway("丸ノ内支線","odpt.Railway:TokyoMetro.MarunouchiBranch"),
                        railway("南北線","odpt.Railway:TokyoMetro.Namboku" ),
                        railway("東西線","odpt.Railway:TokyoMetro.Tozai"),
                        railway("有楽町線","odpt.Railway:TokyoMetro.Yurakucho" )
                )
        );
    }

    private Operator operator(String title) {
        return Operator.builder().title(title).build();
    }

    private Railway railway(String title, String id) {
        return Railway.builder().title(title).sameAs(id).build();
    }
}
