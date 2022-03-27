package net.uoneweb.tokyotrainnow.controller;

import lombok.RequiredArgsConstructor;
import net.uoneweb.tokyotrainnow.entity.CurrentRailway;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class TrainApiController {
    private final TrainService trainService;

    @GetMapping(path = "/api/current-railway/{railwayId}")
    public CurrentRailway get(Model model, @PathVariable String railwayId)  {
        return trainService.getCurrentRailway(railwayId);
    }
}