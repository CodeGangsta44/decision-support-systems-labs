package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.RelationDerivationStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public abstract class AbstractRelationDerivationStrategy implements RelationDerivationStrategy {

    private final String strategyName;

    public AbstractRelationDerivationStrategy(final String strategyName) {

        this.strategyName = strategyName;
    }

    @Override
    public Relation derivate(final CriteriaInfo criteriaInfo) {

        return Relation.builder()
                .relationName(strategyName)
                .matrix(createRelationMatrix(criteriaInfo))
                .elements(generateElementNames(criteriaInfo.getMarks().size()))
                .properties(new HashSet<>())
                .classes(new HashSet<>())
                .build();
    }

    protected List<List<Boolean>> createRelationMatrix(final CriteriaInfo criteriaInfo) {

        return IntStream.range(INTEGER_ZERO, criteriaInfo.getMarks().size())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, criteriaInfo.getMarks().size())
                        .mapToObj(columnIndex -> getCellValue(rowIndex, columnIndex, criteriaInfo))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    protected boolean getCellValue(final int rowIndex, final int columnIndex, final CriteriaInfo criteriaInfo) {

        return Boolean.FALSE;
    }

    private List<String> generateElementNames(final int size) {

        return IntStream.range(INTEGER_ONE, size + INTEGER_ONE)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }
}
