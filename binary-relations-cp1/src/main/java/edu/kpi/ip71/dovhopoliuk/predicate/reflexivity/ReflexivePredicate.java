package edu.kpi.ip71.dovhopoliuk.predicate.reflexivity;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.INTEGER_ZERO;

public class ReflexivePredicate implements Predicate<Relation> {

    @Override
    public boolean test(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .mapToObj(index -> relation.getElement(index, index))
                .allMatch(Boolean.TRUE::equals);
    }
}
