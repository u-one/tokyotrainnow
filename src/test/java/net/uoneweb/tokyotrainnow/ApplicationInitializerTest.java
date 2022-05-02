package net.uoneweb.tokyotrainnow;

import net.uoneweb.tokyotrainnow.service.TrainService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ApplicationInitializerTest {
    @Autowired
    ApplicationContext context;

    @Mock
    private TrainService trainService;

    @InjectMocks
    private ApplicationInitializer initializer;

    @Test
    public void onApplicationEventTest() {
        ContextRefreshedEvent event = new ContextRefreshedEvent(context);
        initializer.onApplicationEvent(event);

        verify(trainService, times(1)).update();
    }
}
