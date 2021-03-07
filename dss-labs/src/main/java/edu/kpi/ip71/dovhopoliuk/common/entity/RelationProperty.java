package edu.kpi.ip71.dovhopoliuk.common.entity;

import edu.kpi.ip71.dovhopoliuk.common.predicate.connectivity.ConnexPredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.cycle.AcyclicPredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.reflexivity.AntiReflexivePredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.reflexivity.ReflexivePredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry.AntiSymmetricPredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry.AsymmetricPredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry.SymmetricPredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.transitivity.NegativelyTransitivePredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.transitivity.TransitivePredicate;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.connectivity.ConnexViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.cycle.AcyclicViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.reflexivity.AntiReflexiveViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.reflexivity.ReflexiveViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.symmetry.AntiSymmetricViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.symmetry.AsymmetricViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.symmetry.SymmetricViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.transitivity.NegativelyTransitiveViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.transitivity.TransitiveViolationFinder;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public enum RelationProperty implements Predicate<Relation> {

    REFLEXIVITY(new ReflexivePredicate(), new ReflexiveViolationFinder()),
    ANTIREFLEXIVITY(new AntiReflexivePredicate(), new AntiReflexiveViolationFinder()),
    SYMMETRY(new SymmetricPredicate(), new SymmetricViolationFinder()),
    ASYMMETRY(new AsymmetricPredicate(), new AsymmetricViolationFinder()),
    ANTISYMMETRY(new AntiSymmetricPredicate(), new AntiSymmetricViolationFinder()),
    TRANSITIVITY(new TransitivePredicate(), new TransitiveViolationFinder()),
    NEGATIVE_TRANSITIVITY(new NegativelyTransitivePredicate(), new NegativelyTransitiveViolationFinder()),
    ACYCLITY(new AcyclicPredicate(), new AcyclicViolationFinder()),
    CONNECTIVITY(new ConnexPredicate(), new ConnexViolationFinder());

    private final Predicate<Relation> predicate;
    private final BiFunction<Relation, RelationProperty, RelationPropertyViolation> violationFinder;

    RelationProperty(final Predicate<Relation> predicate, final BiFunction<Relation, RelationProperty, RelationPropertyViolation> violationFinder) {

        this.predicate = predicate;
        this.violationFinder = violationFinder;
    }

    public boolean test(final Relation relation) {

        return predicate.test(relation);
    }

    public RelationPropertyViolation findViolations(final Relation relation) {

        return violationFinder.apply(relation, this);
    }
}
