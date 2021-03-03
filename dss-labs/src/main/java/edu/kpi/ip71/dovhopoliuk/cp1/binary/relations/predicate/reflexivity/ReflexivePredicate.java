package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.reflexivity;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.constants.Constants.INTEGER_ZERO;

public class ReflexivePredicate implements Predicate<Relation> {

    @Override
    public boolean test(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .mapToObj(index -> relation.getElement(index, index))
                .allMatch(Boolean.TRUE::equals);
    }
}
