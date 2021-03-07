package edu.kpi.ip71.dovhopoliuk.common.predicate.transitivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.util.RelationalOperationsUtil;

import java.util.function.Predicate;

public class NegativelyTransitivePredicate implements Predicate<Relation> {

    private final Predicate<Relation> transitivePredicate = new TransitivePredicate();

    @Override
    public boolean test(final Relation relation) {

        return transitivePredicate.test(RelationalOperationsUtil.invert(relation));
    }
}