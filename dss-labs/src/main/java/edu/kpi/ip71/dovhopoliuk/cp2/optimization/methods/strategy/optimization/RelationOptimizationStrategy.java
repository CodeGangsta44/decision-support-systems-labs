package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

public interface RelationOptimizationStrategy {

    String getOptimizedResult(final Relation relation);

    boolean isApplicable(final Relation relation);
}
