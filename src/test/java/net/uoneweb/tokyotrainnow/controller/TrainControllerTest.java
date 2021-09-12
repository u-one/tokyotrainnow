package net.uoneweb.tokyotrainnow.controller;

import net.uoneweb.tokyotrainnow.entity.Railway;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
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
        /*
        Railway sobuRapid = Railway.builder()
                .id("urn:ucode:_00001C00000000000001000003100E1D")
                .sameAs("odpt.Railway:JR-East.SobuRapid")
                .title("総武快速線").color("#0074BE").lineCode("JO")
                .operator("odpt.Operator:JR-East")
                .railwayTitles(Map.of("en", "Sobu Rapid Line","ja", "総武快速線"))
                .stationOrder(List.of(Railway.Station.builder()
                        .index(1).station("odpt.Station:JR-East.SobuRapid.Tokyo")
                        .stationTitles(Map.of("en", "Tokyo","ja", "東京"))
                        .build()))
                .ascendingRailDirection("odpt.RailDirection:Outbound")
                .descendingRailDirection("odpt.RailDirection:Inbound")
                .build();

        when(service.getRailway("odpt.Railway:JR-East.SobuRapid")).thenReturn(sobuRapid);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("top"))
                .andDo(r -> System.out.println(r.getResponse().getContentAsString()))
                .andExpect(content().string(containsString("東京鉄道Now")));
        */
    }
}
