package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.RelationOptimizationStrategy;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        return "{" + getResultCollection(relation) + "}";
    }

    @Override
    public boolean isApplicable(final Relation relation) {

        return relation.getProperties().containsAll(requiredProperties)
                && relation.getProperties().stream().noneMatch(restrictedProperties::contains);
    }

    protected boolean checkCondition(final Relation relation, final int element) {

        return false;
    }

    private String getResultCollection(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .boxed()
                .filter(element -> checkCondition(relation, element))
                .map(relation::getElementName)
                .collect(Collectors.joining(", "));
    }
}
