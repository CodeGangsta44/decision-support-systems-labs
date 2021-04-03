package edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.optimization;

import edu.kpi.ip71.dovhopoliuk.common.entity.VikorInfo;

import java.util.List;

public interface OptimizationStrategy {

    String optimize(final VikorInfo vikorInfo);

    List<List<Double>> getWeightedMarks(final VikorInfo vikorInfo);

    String optimizeForWeightedMarks(final VikorInfo vikorInfo, final List<List<Double>> weightedMarks);
}
