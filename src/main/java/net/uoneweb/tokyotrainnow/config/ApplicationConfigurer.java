package net.uoneweb.tokyotrainnow.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class ApplicationConfigurer {
    @Autowired
    private RestTemplateCustomizer restTemplateCustomizer;

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();
        restTemplateCustomizer.customize(restTemplate);
        restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter());
        return restTemplate;
    }

    @Bean
    RestTemplateCustomizer customRestTemplateCustomizer() {
        return new RestTemplateConfig();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

}
