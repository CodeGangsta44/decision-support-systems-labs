package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.transitivity;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.util.RelationalOperationsUtil;

import java.util.function.Predicate;

public class NegativelyTransitivePredicate implements Predicate<Relation> {

    private final Predicate<Relation> transitivePredicate = new TransitivePredicate();

    @Override
    public boolean test(final Relation relation) {

        return transitivePredicate.test(RelationalOperationsUtil.invert(relation));
    }
}