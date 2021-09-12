package net.uoneweb.tokyotrainnow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class TokyotrainnowApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(TokyotrainnowApplication.class, args);
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(String.format("Active profiles: %s", Arrays.asList(environment.getActiveProfiles())));
    }
}
