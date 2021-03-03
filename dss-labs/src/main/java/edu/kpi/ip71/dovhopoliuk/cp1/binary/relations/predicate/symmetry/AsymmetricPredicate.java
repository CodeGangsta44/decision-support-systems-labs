package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.symmetry;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.reflexivity.AntiReflexivePredicate;

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
