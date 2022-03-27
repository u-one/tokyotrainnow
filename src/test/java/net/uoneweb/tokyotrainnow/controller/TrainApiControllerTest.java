package net.uoneweb.tokyotrainnow.controller;

import net.uoneweb.tokyotrainnow.CurrentRailwayBuilder;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainApiControllerTest {
    @InjectMocks
    TrainApiController controller;

    @Mock
    TrainService service;

    @Test
    void testGet() {
        CurrentRailway sobuRapid = CurrentRailwayBuilder.subuRapid();
        CurrentRailway expected = CurrentRailwayBuilder.subuRapid();

        when(service.getCurrentRailway("odpt.Railway:JR-East.SobuRapid")).thenReturn(sobuRapid);

        CurrentRailway actual = controller.get(null, "odpt.Railway:JR-East.SobuRapid");

        assertEquals(expected, actual);
    }
}
