package edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.analysis.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.VikorInfo;
import edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.analysis.AnalysisStrategy;
import edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.optimization.OptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.optimization.impl.VikorOptimizationStrategy;

import java.util.List;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;

public class VikorAnalysisStrategy implements AnalysisStrategy {

    private final OptimizationStrategy optimizationStrategy;

    public VikorAnalysisStrategy() {

        this.optimizationStrategy = new VikorOptimizationStrategy();
    }

    @Override
    public void analyze(final VikorInfo vikorInfo) {

        final List<List<Double>> weightedMarks = optimizationStrategy.getWeightedMarks(vikorInfo);

        Stream.iterate(DOUBLE_ZERO, value -> value <= DOUBLE_ONE, value -> value + 0.1)
                .map(value -> Math.round(value * 100.0) / 100.0)
                .forEach(value -> {
                    System.out.println("\n\nV = " + value);
                    vikorInfo.setV(value);
                    optimizationStrategy.optimizeForWeightedMarks(vikorInfo, weightedMarks);
                });
    }
}
