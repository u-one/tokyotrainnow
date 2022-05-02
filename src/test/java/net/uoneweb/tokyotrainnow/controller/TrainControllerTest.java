package net.uoneweb.tokyotrainnow.controller;

import net.uoneweb.tokyotrainnow.TestDataBuilder;
import net.uoneweb.tokyotrainnow.config.SiteConfig;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainControllerTest {

    @InjectMocks
    TrainController controller;

    @Mock
    TrainService service;

    @Mock
    SiteConfig siteConfig;

    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    public void tear() throws Exception {
        closeable.close();
    }

    @Test
    public void testTop() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("top"))
                .andDo(r -> System.out.println(r.getResponse().getContentAsString()));
    }

    @Test
    public void testCurrentRailway() throws Exception {
        mockMvc.perform(get("/current-railway/odpt.Railway:JR-East.SobuRapid"))
                .andExpect(status().isOk())
                .andExpect(view().name("current-railway"))
                .andDo(r -> System.out.println(r.getResponse().getContentAsString()));

        when(service.getRailway("odpt.Railway:JR-East.SobuRapid"))
                .thenReturn(TestDataBuilder.soubuRapid());
    }

    @Test
    public void testCurrentRailwayInvalid() throws Exception {
        mockMvc.perform(get("/current-railway/invalid"))
                .andExpect(status().isOk())
                .andExpect(view().name("current-railway"))
                .andDo(r -> System.out.println(r.getResponse().getContentAsString()));

        when(service.getRailway("invalid"))
                .thenReturn(null);
    }
}
