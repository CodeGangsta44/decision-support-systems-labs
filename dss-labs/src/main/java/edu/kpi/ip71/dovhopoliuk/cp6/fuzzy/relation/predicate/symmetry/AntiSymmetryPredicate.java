package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.symmetry;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class AntiSymmetryPredicate implements Predicate<FuzzyRelation> {

    @Override
    public boolean test(final FuzzyRelation fuzzyRelation) {

        return IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                .allMatch(rowIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                        .filter(columnIndex -> columnIndex != rowIndex)
                        .allMatch(columnIndex -> DOUBLE_ZERO == Math.min(fuzzyRelation.getElement(rowIndex, columnIndex),
                                fuzzyRelation.getElement(columnIndex, rowIndex))));
    }
}
