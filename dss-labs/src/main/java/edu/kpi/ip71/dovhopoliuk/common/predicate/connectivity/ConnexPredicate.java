package edu.kpi.ip71.dovhopoliuk.common.predicate.connectivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.predicate.reflexivity.ReflexivePredicate;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ConnexPredicate implements Predicate<Relation> {

    private final Predicate<Relation> reflexivePredicate = new ReflexivePredicate();

    @Override
    public boolean test(final Relation relation) {

        return reflexivePredicate.test(relation) && checkOnSymmetricZeros(relation);
    }

    private boolean checkOnSymmetricZeros(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize() - INTEGER_ONE)
                .allMatch(rowIndex -> IntStream.range(rowIndex + INTEGER_ONE, relation.getSize())
                        .allMatch(columnIndex -> isSymmetricElementsNotZeros(relation, rowIndex, columnIndex)));
    }

    private boolean isSymmetricElementsNotZeros(final Relation relation, final int rowIndex, final int columnIndex) {

        return relation.getElement(rowIndex, columnIndex)
                ||
                relation.getElement(columnIndex, rowIndex);
    }
}
