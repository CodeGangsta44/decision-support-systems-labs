package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ReflexivityPredicate implements Predicate<FuzzyRelation> {

    @Override
    public boolean test(final FuzzyRelation fuzzyRelation) {

        return IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                .allMatch(index -> DOUBLE_ONE == fuzzyRelation.getElement(index, index));
    }
}
