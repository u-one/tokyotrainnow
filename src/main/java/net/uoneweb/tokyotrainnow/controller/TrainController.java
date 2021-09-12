package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.Railway;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class TrainController {
    @Autowired
    private TrainService trainService;

    @GetMapping(path = {"/"})
    public String top(Model model) {
        Railway railway = trainService.getRailway("odpt.Railway:JR-East.SobuRapid");
        if (railway != null) {
            model.addAttribute(railway);
        } else {
            model.addAttribute(Railway.builder().color("#888888").title("Dummy").stationOrder(List.of()).build());
        }
        return "top";
    }

}
