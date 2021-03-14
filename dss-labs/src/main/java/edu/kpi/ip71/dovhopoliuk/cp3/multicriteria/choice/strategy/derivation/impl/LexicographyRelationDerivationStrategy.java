package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.util.RelationalOperationsUtil;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_MINUS_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class LexicographyRelationDerivationStrategy extends AbstractRelationDerivationStrategy {

    private static final String STRATEGY_NAME = "Lexicography";

    public LexicographyRelationDerivationStrategy() {

        super(STRATEGY_NAME);
    }

    @Override
    protected boolean getCellValue(final int rowIndex, final int columnIndex, final CriteriaInfo criteriaInfo) {

        return INTEGER_ONE == getFirstNonZeroSign(rowIndex, columnIndex, criteriaInfo);
    }

    private int getFirstNonZeroSign(final int rowIndex, final int columnIndex, final CriteriaInfo criteriaInfo) {

        return criteriaInfo.getStrictOrderOfImportance().stream()
                .map(index -> RelationalOperationsUtil.sign(criteriaInfo.getMark(rowIndex, index) - criteriaInfo.getMark(columnIndex, index)))
                .filter(sign -> sign != INTEGER_ZERO)
                .findFirst()
                .orElse(INTEGER_MINUS_ONE);
    }
}
