package edu.kpi.ip71.dovhopoliuk.cp5.topsis;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.common.entity.TopsisInfo;
import edu.kpi.ip71.dovhopoliuk.cp5.topsis.strategy.optimizations.impl.TopsisOptimizationStrategy;


public class Main {

    public static void main(final String... args) {

        TopsisInfo topsisInfo = new ReadInputController().readTopsisInput("cp5/topsis/input.txt");

        System.out.println("-=== TOPSIS WITH ONLY MAXIMIZATION ===-");
        new TopsisOptimizationStrategy(Boolean.TRUE).optimize(topsisInfo);

        System.out.println("\n\n-=== TOPSIS WITH MAXIMIZATION AND MINIMIZATION ===-");
        new TopsisOptimizationStrategy(Boolean.FALSE).optimize(topsisInfo);
    }
}
