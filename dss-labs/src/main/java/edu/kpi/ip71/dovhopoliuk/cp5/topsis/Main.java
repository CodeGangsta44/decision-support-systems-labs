package edu.kpi.ip71.dovhopoliuk.cp5.topsis;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.common.entity.TopsisInfo;
import edu.kpi.ip71.dovhopoliuk.cp5.topsis.strategy.optimizations.impl.TopsisOptimizationStrategy;


public class Main {

    public static void main(final String... args) {

        TopsisInfo topsisInfo = new ReadInputController().readTopsisInput("cp5/topsis/input.txt");

        new TopsisOptimizationStrategy(Boolean.TRUE).optimize(topsisInfo);
        new TopsisOptimizationStrategy(Boolean.FALSE).optimize(topsisInfo);
    }
}
