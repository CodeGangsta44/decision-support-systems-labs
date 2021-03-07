package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.domination;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.AbstractRelationOptimizationStrategy;

import java.util.Collections;
import java.util.Set;

public class RelationDominationOptimizationStrategy extends AbstractRelationOptimizationStrategy {

    private static final Set<RelationProperty> requiredProperties = Collections.emptySet();
    private static final Set<RelationProperty> restrictedProperties = Set.of(RelationProperty.ASYMMETRY);

    public RelationDominationOptimizationStrategy() {

        super(requiredProperties, restrictedProperties);
    }

    protected boolean checkCondition(final Relation relation, final int element) {

        return relation.getLowerSection(element)
                .containsAll(relation.getRelationSet());
    }
}
