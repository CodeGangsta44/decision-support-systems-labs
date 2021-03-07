package edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class AntiSymmetricPredicate implements Predicate<Relation> {

    @Override
    public boolean test(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize() - INTEGER_ONE)
                .allMatch(rowIndex -> IntStream.range(rowIndex + INTEGER_ONE, relation.getSize())
                        .allMatch(columnIndex -> isSymmetricElementsNotEquals(relation, rowIndex, columnIndex)));
    }

    private boolean isSymmetricElementsNotEquals(final Relation relation, final int rowIndex, final int columnIndex) {

        return !(relation.getElement(rowIndex, columnIndex)
                &&
                relation.getElement(columnIndex, rowIndex));
    }
}
