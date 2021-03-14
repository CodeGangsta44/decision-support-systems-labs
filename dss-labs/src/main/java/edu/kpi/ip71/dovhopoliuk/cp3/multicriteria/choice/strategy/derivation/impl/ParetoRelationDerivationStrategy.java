package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.util.RelationalOperationsUtil;

import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ParetoRelationDerivationStrategy extends AbstractRelationDerivationStrategy {

    private static final String STRATEGY_NAME = "Pareto";

    public ParetoRelationDerivationStrategy() {

        super(STRATEGY_NAME);
    }

    @Override
    protected boolean getCellValue(final int rowIndex, final int columnIndex, final CriteriaInfo criteriaInfo) {

        return IntStream.range(INTEGER_ZERO, criteriaInfo.getQuantityOfCriteria())
                .mapToObj(index ->
                        RelationalOperationsUtil.sign(criteriaInfo.getMark(rowIndex, index) - criteriaInfo.getMark(columnIndex, index)))
                .allMatch(sign -> sign >= INTEGER_ZERO);
    }
}
