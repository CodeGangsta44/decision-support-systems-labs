package edu.kpi.ip71.dovhopoliuk.common.entity;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

public enum RelationClass implements Predicate<Relation> {

    EQUIVALENCE(Set.of(
            RelationProperty.REFLEXIVITY,
            RelationProperty.SYMMETRY,
            RelationProperty.TRANSITIVITY)),

    STRICT_ORDER(Set.of(
            RelationProperty.ANTIREFLEXIVITY,
            RelationProperty.ASYMMETRY,
            RelationProperty.TRANSITIVITY,
            RelationProperty.ACYCLITY)),

    NOT_STRICT_ORDER(Set.of(
            RelationProperty.REFLEXIVITY,
            RelationProperty.ANTISYMMETRY,
            RelationProperty.TRANSITIVITY)),

    QUASI_ORDER(Set.of(
            RelationProperty.REFLEXIVITY,
            RelationProperty.TRANSITIVITY)),

    WEAK_ORDERING(Set.of(
            RelationProperty.ASYMMETRY,
            RelationProperty.TRANSITIVITY,
            RelationProperty.NEGATIVE_TRANSITIVITY)),

    TOLERANCE(Set.of(
            RelationProperty.REFLEXIVITY,
            RelationProperty.SYMMETRY)),

    UNDEFINED(Collections.emptySet());

    private final Set<RelationProperty> requiredProperties;

    RelationClass(final Set<RelationProperty> requiredProperties) {

        this.requiredProperties = requiredProperties;
    }

    public boolean test(final Relation relation) {

        return relation.getProperties().containsAll(requiredProperties);
    }
}
