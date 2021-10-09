package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
public class TrainController {
    @Autowired
    private TrainService trainService;

    @GetMapping(path = {"/"})
    public String top(Model model) {
        return "top";
    }

    @GetMapping(path = {"/current-railway/{railwayId}"})
    public String currentRailway(Model model, @PathVariable String railwayId) {
        CurrentRailway railway = trainService.getCurrentRailway(railwayId);
        if (railway != null) {
            model.addAttribute("railway", railway);
        } else {
            model.addAttribute("railway", CurrentRailway.builder().color("#888888").title("Dummy").sections(List.of()).build());
        }
        return "current-railway";
    }

}
