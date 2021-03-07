package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.domination;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.AbstractRelationOptimizationStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;

public class StrictRelationDominationOptimizationStrategy extends AbstractRelationOptimizationStrategy {

    private static final Set<RelationProperty> requiredProperties = Collections.emptySet();
    private static final Set<RelationProperty> restrictedProperties = Set.of(RelationProperty.ASYMMETRY);

    public StrictRelationDominationOptimizationStrategy() {

        super(requiredProperties, restrictedProperties);
    }

    protected boolean checkCondition(final Relation relation, final int element) {

        return relation.getLowerSection(element).containsAll(relation.getRelationSet())
                && checkUpperSection(relation.getUpperSection(element), element);
    }

    private boolean checkUpperSection(final List<Integer> upperSection, final int element) {

        return upperSection.contains(element) && upperSection.size() == INTEGER_ONE;
    }
}
