package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TrainController {

    @GetMapping(path = {"/"})
    public String top(Model model) {
        return "top";
    }

}
