package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.RelationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.blocking.AsymmetricRelationBlockingOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.blocking.RelationBlockingOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.blocking.StrictRelationBlockingOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.domination.AsymmetricRelationDominationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.domination.RelationDominationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.domination.StrictRelationDominationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.SolvingStrategy;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DominationAndBlockingSolvingStrategy implements SolvingStrategy {

    private static final Map<String, RelationOptimizationStrategy> optimizationStrategies =
            Map.ofEntries(
                    Map.entry("X*P", new AsymmetricRelationDominationOptimizationStrategy()),
                    Map.entry("X*R", new RelationDominationOptimizationStrategy()),
                    Map.entry("X**R", new StrictRelationDominationOptimizationStrategy()),
                    Map.entry("X0P", new AsymmetricRelationBlockingOptimizationStrategy()),
                    Map.entry("X0R", new RelationBlockingOptimizationStrategy()),
                    Map.entry("X00R", new StrictRelationBlockingOptimizationStrategy())
            );

    @Override
    public String solve(final Relation relation) {

        analyzeAsymmetry(relation);

        return optimizationStrategies.entrySet().stream()
                .filter(entry -> entry.getValue().isApplicable(relation))
                .map(entry -> entry.getKey() + " = " + entry.getValue().getOptimizedResult(relation))
                .collect(Collectors.joining("\n"));
    }

    private void analyzeAsymmetry(final Relation relation) {

        Optional.of(RelationProperty.ASYMMETRY)
                .filter(property -> property.test(relation))
                .ifPresent(property -> relation.getProperties().add(property));
    }
}
