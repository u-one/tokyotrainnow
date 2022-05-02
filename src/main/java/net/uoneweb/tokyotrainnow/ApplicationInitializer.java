package net.uoneweb.tokyotrainnow;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"dev", "smoke-test"})
@Component
public class ApplicationInitializer {

    @Autowired
    TrainService trainService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("onApplicationEvent");
        trainService.update();
    }
}
