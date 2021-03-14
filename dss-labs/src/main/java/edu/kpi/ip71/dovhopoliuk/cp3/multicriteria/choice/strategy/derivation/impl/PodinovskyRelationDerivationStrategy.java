package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.RelationDerivationStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PodinovskyRelationDerivationStrategy extends AbstractRelationDerivationStrategy {

    private static final String STRATEGY_NAME = "Podinovsky";

    private final RelationDerivationStrategy paretoRelationDerivationStrategy;

    public PodinovskyRelationDerivationStrategy() {

        super(STRATEGY_NAME);

        this.paretoRelationDerivationStrategy = new ParetoRelationDerivationStrategy();
    }

    @Override
    protected List<List<Boolean>> createRelationMatrix(final CriteriaInfo criteriaInfo) {

        return paretoRelationDerivationStrategy.derivate(buildCriteriaInfoWithSortedMarks(criteriaInfo)).getMatrix();
    }

    private CriteriaInfo buildCriteriaInfoWithSortedMarks(final CriteriaInfo criteriaInfo) {

        return CriteriaInfo.builder()
                .marks(createMatrixWithSortedMarks(criteriaInfo))
                .strictOrderOfImportance(criteriaInfo.getStrictOrderOfImportance())
                .invertedQuasiOrderOfImportance(criteriaInfo.getInvertedQuasiOrderOfImportance())
                .build();
    }

    private List<List<Integer>> createMatrixWithSortedMarks(final CriteriaInfo criteriaInfo) {

        return criteriaInfo.getMarks()
                .stream()
                .map(marks -> marks.stream()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
