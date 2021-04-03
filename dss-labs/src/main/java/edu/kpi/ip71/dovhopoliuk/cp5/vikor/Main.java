package edu.kpi.ip71.dovhopoliuk.cp5.vikor;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.common.entity.VikorInfo;
import edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.analysis.impl.VikorAnalysisStrategy;
import edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.optimization.impl.VikorOptimizationStrategy;

public class Main {

    public static void main(final String... args) {

        VikorInfo vikorInfo = new ReadInputController().readVikorInput("cp5/vikor/input.txt");

        System.out.println("-=== STARTING OPTIMIZATION PHASE ===-");
        new VikorOptimizationStrategy().optimize(vikorInfo);

        System.out.println("\n\n-=== STARTING ANALYSIS PHASE ===-");
        new VikorAnalysisStrategy().analyze(vikorInfo);
    }
}
