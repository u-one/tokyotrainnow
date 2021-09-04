package net.uoneweb.tokyotrainnow.view;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainView {

    private String operator;

    private String railway;

    private String railDirection;

    private String trainNumber;

    private String fromStation;

    private String toStation;

    private String destinationStation;

    private int index;

    private String delay;

    private String carComposition;
}

