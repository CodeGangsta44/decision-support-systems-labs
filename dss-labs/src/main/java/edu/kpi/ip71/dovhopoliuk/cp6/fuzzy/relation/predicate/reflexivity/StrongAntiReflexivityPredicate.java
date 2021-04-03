package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class StrongAntiReflexivityPredicate implements Predicate<FuzzyRelation> {

    private final Predicate<FuzzyRelation> antiReflexivityPredicate;

    public StrongAntiReflexivityPredicate() {

        this.antiReflexivityPredicate = new AntiReflexivityPredicate();
    }

    @Override
    public boolean test(final FuzzyRelation fuzzyRelation) {

        return antiReflexivityPredicate.test(fuzzyRelation)
                &&
                IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                        .allMatch(rowIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                                .filter(columnIndex -> columnIndex != rowIndex)
                                .allMatch(columnIndex -> fuzzyRelation.getElement(rowIndex, columnIndex) > DOUBLE_ZERO));
    }
}
