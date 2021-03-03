package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.transitivity;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.constants.Constants.INTEGER_ZERO;

public class TransitivePredicate implements Predicate<Relation> {

    @Override
    public boolean test(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .allMatch(rowIndex -> checkTransitivityConditionForRow(relation, rowIndex));
    }

    private boolean checkTransitivityConditionForRow(final Relation relation, final int indexToCheck) {

        return relation.getLowerSection(indexToCheck).stream()
                .allMatch(rowIndex -> IntStream.range(INTEGER_ZERO, relation.getSize())
                        .filter(columnIndex -> relation.getElement(rowIndex, columnIndex))
                        .allMatch(columnIndex -> relation.getElement(indexToCheck, columnIndex)));
    }
}
