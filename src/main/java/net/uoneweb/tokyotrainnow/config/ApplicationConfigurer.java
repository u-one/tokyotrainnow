package net.uoneweb.tokyotrainnow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfigurer {
    @Autowired
    private RestTemplateCustomizer restTemplateCustomizer;

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();
        restTemplateCustomizer.customize(restTemplate);
        return restTemplate;
    }
}