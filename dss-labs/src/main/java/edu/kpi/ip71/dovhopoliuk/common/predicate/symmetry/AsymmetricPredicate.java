package edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.predicate.reflexivity.AntiReflexivePredicate;

import java.util.function.Predicate;

public class AsymmetricPredicate implements Predicate<Relation> {

    private final Predicate<Relation> antiReflexivePredicate = new AntiReflexivePredicate();
    private final Predicate<Relation> antiSymmetricPredicate = new AntiSymmetricPredicate();

    @Override
    public boolean test(final Relation relation) {

        return antiReflexivePredicate.test(relation)
                && antiSymmetricPredicate.test(relation);
    }
}
