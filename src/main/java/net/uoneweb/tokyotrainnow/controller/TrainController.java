package net.uoneweb.tokyotrainnow.controller;

import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.client.OdptApiClientConfig;
import net.uoneweb.tokyotrainnow.entity.Operator;
import net.uoneweb.tokyotrainnow.entity.Railway;
import net.uoneweb.tokyotrainnow.entity.Station;
import net.uoneweb.tokyotrainnow.entity.Train;
import net.uoneweb.tokyotrainnow.service.TrainService;
import net.uoneweb.tokyotrainnow.view.TrainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class TrainController {
    @Autowired
    private TrainService trainService;

    @Autowired
    private OdptApiClientConfig apiClientConfig;

    @GetMapping(path = {"/"})
    public String top(Model model) {
        List<Operator> operators = trainService.getOperators();

        List<Railway> railways = trainService.getRailways();
        List<String> trainOperatorIds = railways.stream().map(rw -> rw.getOperator()).distinct().collect(Collectors.toList());
        List<Operator> trainOperators = operators.stream().filter(op -> trainOperatorIds.contains(op.getSameAs())).collect(Collectors.toList());
        model.addAttribute("operators", trainOperators);
        model.addAttribute("railways", railways);

        model.addAttribute("apiendpoint", apiClientConfig.getEndpoint());
        model.addAttribute("apitoken", apiClientConfig.getKey());
        return "top";
    }

    public static class RailwayComparator implements Comparator<Railway> {
        @Override
        public int compare(Railway l, Railway r) {
            if (l.getOperator().compareTo(r.getOperator()) < 0) {
                return -1;
            } else if (l.getOperator().compareTo(r.getOperator()) > 0) {
                return 1;
            } else {
                if (l.getSameAs().compareTo(r.getSameAs()) < 0){
                    return -1;
                } else if (l.getSameAs().compareTo(r.getSameAs()) > 0) {
                    return 1;
                }
            }
            return 0;
        }
    }

    @GetMapping(path = {"/operators"})
    public String operators(Model model) {
        List<Operator> operators = trainService.getOperators();
        model.addAttribute("operators", operators);
        return "operators";
    }

    @GetMapping(path = {"/train_operators"})
    public String trainOperators(Model model) {
        List<Operator> operators = trainService.getOperators();
        List<Railway> railways = trainService.getRailways();
        List<String> trainOperatorIds = railways.stream().map(rw -> rw.getOperator()).distinct().collect(Collectors.toList());
        List<Operator> trainOperators = operators.stream().filter(op -> trainOperatorIds.contains(op.getSameAs())).collect(Collectors.toList());
        model.addAttribute("operators", trainOperators);
        return "operators";
    }

    @GetMapping(path = {"/railways"})
    public String railways(Model model) {
        List<Railway> railways = trainService.getRailways();
        Collections.sort(railways, new RailwayComparator());
        model.addAttribute("railways", railways);
        return "railways";
    }

    @GetMapping(path = {"/railway/{railwayId}"})
    public String railwayPath(Model model, @PathVariable String railwayId) {
        Railway railway = trainService.getRailway(railwayId);
        model.addAttribute("railway", railway);
        return "railway";
    }

    @GetMapping(path = {"/railway"})
    public String railway(Model model, @RequestParam(value = "railway", required = true) String railwayId) {
        Railway railway = trainService.getRailway(railwayId);
        model.addAttribute("railway", railway);
        return "railway";
    }

    @GetMapping(path = {"/railways/{operatorId}"})
    public String railways(Model model, @PathVariable String operatorId) {
        List<Railway> railways = trainService.getRailways(operatorId);
        Collections.sort(railways, new RailwayComparator());
        model.addAttribute("railways", railways);
        return "railways";
    }

    @GetMapping(path = {"/maps"})
    public String maps(Model model) {
        return "maps";
    }

    @GetMapping(path = {"/train"})
    public String train(@RequestParam(value = "operator", required = false) String operator,
                        @RequestParam(value = "railway", required = false) String railway,
                        Model model) {

        List<Train> trains;
        if (StringUtils.hasText(operator) && StringUtils.hasText(railway)) {
            trains = trainService.getTrain(operator, railway);
        } else {
            trains = trainService.getTrains();
        }

        List<TrainView> trainViews = new ArrayList();
        for (Train train : trains) {

            List<String> dsts = train.getDestinationStation();
            String dstStr = dsts.stream().map((dst) -> {
                   Optional<Station> opt = trainService.getStation(dst);
                   if (opt.isPresent()) {
                       return opt.get().getTitle();
                   } else {
                       return "-";
                   }
            }).collect(Collectors.joining("・"));
            String from = trainService.getStation(train.getFromStation()).map(stn -> stn.getTitle()).orElseGet(() -> "");
            String to = trainService.getStation(train.getToStation()).map(stn -> stn.getTitle()).orElseGet(() -> "");
            String delay = train.getDelay() != 0 ? String.format("%d分遅れ", train.getDelay()) : "";

            TrainView trainView = TrainView.builder()
                    .trainNumber(train.getTrainNumber())
                    .operator(train.getOperator())
                    .railway(train.getRailway())
                    .carComposition(String.format("%s両編成", train.getCarComposition()))
                    .delay(delay)
                    .fromStation(from)
                    .toStation(to)
                    .destinationStation(dstStr)
                    .railDirection(train.getRailDirection())
                    .build();
            trainViews.add(trainView);
            log.info(train.toString()+ "," + dstStr);
        }
        model.addAttribute("trains", trainViews);
        return "train";
    }

    @GetMapping(path = {"/train/{trainNumber}"})
    public String train(Model model, @PathVariable String trainNumber) {
        /*
        List<Railway> railways = trainService.getRailways(operatorId);
        Collections.sort(railways, new RailwayComparator());
        model.addAttribute("railways", railways);
         */
        return "railways";
    }

    @GetMapping(path = {"/stations"})
    public String stations(Model model) {
        List<Station> stations = trainService.getStations();
        model.addAttribute("stations", stations);
        return "stations";
    }
}
