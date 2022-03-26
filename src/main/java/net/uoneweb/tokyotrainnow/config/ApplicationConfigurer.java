package net.uoneweb.tokyotrainnow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

/*
import org.springframework.cloud.gcp.autoconfigure.datastore.DatastoreProvider;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTransactionManager;
import org.springframework.cloud.gcp.data.datastore.core.convert.DatastoreCustomConversions;
 */

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

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    /*
    @Bean
    DatastoreTransactionManager datastoreTransactionManager(DatastoreProvider datastore) {
        return new DatastoreTransactionManager(datastore);
    }

    @Bean
    public DatastoreCustomConversions datastoreCustomConversions() {
        return new DatastoreCustomConversions(Arrays.asList(Railway.RAILWAYSTATION_STRING_CONVERTER, Railway.STRING_RAILWAYSTATION_CONVERTER));
    }
     */
}