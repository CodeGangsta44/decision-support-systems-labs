package edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class SymmetricPredicate implements Predicate<Relation> {

    @Override
    public boolean test(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .allMatch(rowIndex -> IntStream.range(rowIndex, relation.getSize())
                        .allMatch(columnIndex -> isSymmetricElementsEquals(relation, columnIndex, rowIndex)));
    }

    private boolean isSymmetricElementsEquals(final Relation relation, final int rowIndex, final int columnIndex) {

        return relation.getElement(rowIndex, columnIndex)
                .equals(relation.getElement(columnIndex, rowIndex));
    }
}