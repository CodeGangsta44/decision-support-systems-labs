package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.connectivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class StrongConnectivityPredicate implements Predicate<FuzzyRelation> {

    @Override
    public boolean test(final FuzzyRelation fuzzyRelation) {

        return IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                .allMatch(rowIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                        .allMatch(columnIndex -> DOUBLE_ONE == Math.min(fuzzyRelation.getElement(rowIndex, columnIndex),
                                fuzzyRelation.getElement(columnIndex, rowIndex))));
    }
}
