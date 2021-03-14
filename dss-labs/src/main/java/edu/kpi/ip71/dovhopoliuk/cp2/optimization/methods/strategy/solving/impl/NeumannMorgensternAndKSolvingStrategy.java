package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.RelationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.k.KOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.nm.RelationNeumannMorgensternOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.SolvingStrategy;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NeumannMorgensternAndKSolvingStrategy implements SolvingStrategy {

    private static final Map<String, RelationOptimizationStrategy> optimizationStrategies =
            Map.ofEntries(
                    Map.entry("Neumann-Morgenstern optimization", new RelationNeumannMorgensternOptimizationStrategy()),
                    Map.entry("K-optimization", new KOptimizationStrategy())
            );

    @Override
    public String solve(final Relation relation) {

        analyzeAcyclity(relation);

        return optimizationStrategies.entrySet().stream()
                .filter(entry -> entry.getValue().isApplicable(relation))
                .map(entry -> "\n" + entry.getKey() + ":\n" + entry.getValue().getOptimizedResult(relation))
                .collect(Collectors.joining("\n"));
    }

    private void analyzeAcyclity(final Relation relation) {

        Optional.of(RelationProperty.ACYCLITY)
                .filter(property -> property.test(relation))
                .ifPresent(property -> relation.getProperties().add(property));
    }
}
