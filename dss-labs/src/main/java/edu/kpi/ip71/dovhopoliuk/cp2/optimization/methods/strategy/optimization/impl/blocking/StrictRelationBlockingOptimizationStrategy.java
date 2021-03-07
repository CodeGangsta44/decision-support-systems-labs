package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.blocking;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.AbstractRelationOptimizationStrategy;

import java.util.Collections;
import java.util.Set;

public class StrictRelationBlockingOptimizationStrategy extends AbstractRelationOptimizationStrategy {

    private static final Set<RelationProperty> requiredProperties = Collections.emptySet();
    private static final Set<RelationProperty> restrictedProperties = Set.of(RelationProperty.ASYMMETRY);

    public StrictRelationBlockingOptimizationStrategy() {

        super(requiredProperties, restrictedProperties);
    }

    protected boolean checkCondition(final Relation relation, final int element) {

        return relation.getUpperSection(element)
                .stream()
                .allMatch(member -> element == member);
    }
}
