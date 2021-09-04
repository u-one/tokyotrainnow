package net.uoneweb.tokyotrainnow.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("net.uoneweb.tokyotrainnow.odptapi")
@Data
public class OdptApiClientConfig {
    private String key;
    private String endpoint;
}
