package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.strategy.solving;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.utils.FuzzyRelationOperationUtils;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.FuzzyRelationPropertyViolationFindingStrategy;

import java.util.Arrays;
import java.util.List;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class DefaultSolvingStrategy {

    private static final double ALPHA = 0.5;

    private final FuzzyRelationPropertyViolationFindingStrategy violationFindingStrategy = new FuzzyRelationPropertyViolationFindingStrategy();

    public void solve(final List<FuzzyRelation> fuzzyRelations) {

        analyzeProperties(fuzzyRelations.get(INTEGER_ZERO));
        findViolations(fuzzyRelations.get(INTEGER_ZERO));

        fuzzyRelations.get(INTEGER_ZERO).print();

        demonstrateOperations(fuzzyRelations.get(INTEGER_ZERO), fuzzyRelations.get(INTEGER_ONE));
    }

    private void analyzeProperties(final FuzzyRelation fuzzyRelation) {

        Arrays.stream(FuzzyRelationProperty.values())
                .filter(property -> property.test(fuzzyRelation))
                .forEach(property -> fuzzyRelation.getProperties().add(property));
    }

    private void findViolations(final FuzzyRelation fuzzyRelation) {

        violationFindingStrategy.findViolations(fuzzyRelation);
    }

    private void demonstrateOperations(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        FuzzyRelationOperationUtils.union(relation1, relation2).print();
        FuzzyRelationOperationUtils.intersect(relation1, relation2).print();
        FuzzyRelationOperationUtils.complement(relation1).print();
        FuzzyRelationOperationUtils.complement(relation2).print();
        FuzzyRelationOperationUtils.composition(relation1, relation2).print();
        FuzzyRelationOperationUtils.buildAlphaLevel(relation1, ALPHA).print();
        FuzzyRelationOperationUtils.buildStrictAdvantage(relation1).print();
    }
}
