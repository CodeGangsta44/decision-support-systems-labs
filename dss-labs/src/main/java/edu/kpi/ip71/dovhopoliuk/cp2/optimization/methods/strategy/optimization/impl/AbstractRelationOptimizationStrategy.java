package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.RelationOptimizationStrategy;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public abstract class AbstractRelationOptimizationStrategy implements RelationOptimizationStrategy {

    private final Set<RelationProperty> requiredProperties;

    private final Set<RelationProperty> restrictedProperties;

    public AbstractRelationOptimizationStrategy(final Set<RelationProperty> requiredProperties,
                                                final Set<RelationProperty> restrictedProperties) {

        this.requiredProperties = requiredProperties;
        this.restrictedProperties = restrictedProperties;
    }

    @Override
    public String getOptimizedResult(final Relation relation) {

        return "{" + getResultStream(relation)
                .map(relation::getElementName)
                .collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public boolean isApplicable(final Relation relation) {

        return relation.getProperties().containsAll(requiredProperties)
                && relation.getProperties().stream().noneMatch(restrictedProperties::contains);
    }

    @Override
    public Set<Integer> getOptimizedResultAsSet(final Relation relation) {

        return getResultStream(relation).collect(Collectors.toSet());
    }

    protected boolean checkCondition(final Relation relation, final int element) {

        return false;
    }

    private Stream<Integer> getResultStream(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .boxed()
                .filter(element -> checkCondition(relation, element));
    }
}
