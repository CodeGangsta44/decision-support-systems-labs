package edu.kpi.ip71.dovhopoliuk.common.entity;

import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.connectivity.StrongConnectivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.connectivity.WeakConnectivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity.AntiReflexivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity.ReflexivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity.StrongAntiReflexivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity.StrongReflexivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity.WeakAntiReflexivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.reflexivity.WeakReflexivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.symmetry.AntiSymmetryPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.symmetry.AsymmetryPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.symmetry.SymmetryPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.predicate.transitivity.TransitivityPredicate;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.connectivity.StrongConnectivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.connectivity.WeakConnectivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity.AntiReflexivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity.ReflexivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity.StrongAntiReflexivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity.StrongReflexivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity.WeakAntiReflexivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity.WeakReflexivityViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.symmetry.AntiSymmetryViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.symmetry.AsymmetryViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.symmetry.SymmetryViolationFinder;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.transitivity.TransitivityViolationFinder;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public enum FuzzyRelationProperty implements Predicate<FuzzyRelation> {

    REFLEXIVITY(new ReflexivityPredicate(), new ReflexivityViolationFinder()),
    STRONG_REFLEXIVITY(new StrongReflexivityPredicate(), new StrongReflexivityViolationFinder()),
    WEAK_REFLEXIVITY(new WeakReflexivityPredicate(), new WeakReflexivityViolationFinder()),

    ANTI_REFLEXIVITY(new AntiReflexivityPredicate(), new AntiReflexivityViolationFinder()),
    STRONG_ANTIREFLEXIVITY(new StrongAntiReflexivityPredicate(), new StrongAntiReflexivityViolationFinder()),
    WEAK_ANTIREFLEXIVITY(new WeakAntiReflexivityPredicate(), new WeakAntiReflexivityViolationFinder()),

    SYMMETRY(new SymmetryPredicate(), new SymmetryViolationFinder()),
    ASYMMETRY(new AsymmetryPredicate(), new AsymmetryViolationFinder()),
    ANTISYMMETRY(new AntiSymmetryPredicate(), new AntiSymmetryViolationFinder()),

    TRANSITIVITY(new TransitivityPredicate(), new TransitivityViolationFinder()),

    STRONG_CONNECTIVITY(new StrongConnectivityPredicate(), new StrongConnectivityViolationFinder()),
    WEAK_CONNECTIVITY(new WeakConnectivityPredicate(), new WeakConnectivityViolationFinder());

    private final Predicate<FuzzyRelation> predicate;
    private final BiFunction<FuzzyRelation, FuzzyRelationProperty, FuzzyRelationPropertyViolation> violationFinder;

    FuzzyRelationProperty(final Predicate<FuzzyRelation> predicate,
                          final BiFunction<FuzzyRelation, FuzzyRelationProperty, FuzzyRelationPropertyViolation> violationFinder) {

        this.predicate = predicate;
        this.violationFinder = violationFinder;
    }

    @Override
    public boolean test(final FuzzyRelation relation) {

        return predicate.test(relation);
    }

    public FuzzyRelationPropertyViolation findViolations(final FuzzyRelation relation) {

        return violationFinder.apply(relation, this);
    }

}
