package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.transitivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class TransitivityPredicate implements Predicate<FuzzyRelation> {

    @Override
    public boolean test(final FuzzyRelation fuzzyRelation) {

        return IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                .allMatch(xIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                        .allMatch(yIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                                .allMatch(zIndex -> fuzzyRelation.getElement(xIndex, zIndex) >=
                                        Math.min(fuzzyRelation.getElement(xIndex, yIndex), fuzzyRelation.getElement(yIndex, zIndex)))));
    }
}
