package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.blocking;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.AbstractRelationOptimizationStrategy;

import java.util.Collections;
import java.util.Set;

public class AsymmetricRelationBlockingOptimizationStrategy extends AbstractRelationOptimizationStrategy {

    private static final Set<RelationProperty> requiredProperties = Set.of(RelationProperty.ASYMMETRY);
    private static final Set<RelationProperty> restrictedProperties = Collections.emptySet();

    public AsymmetricRelationBlockingOptimizationStrategy() {

        super(requiredProperties, restrictedProperties);
    }

    protected boolean checkCondition(final Relation relation, final int element) {

        return relation.getUpperSection(element).isEmpty();
    }
}
