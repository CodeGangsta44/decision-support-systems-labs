package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.symmetry;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationPropertyViolation;
import edu.kpi.ip71.dovhopoliuk.common.predicate.reflexivity.AntiReflexivePredicate;
import edu.kpi.ip71.dovhopoliuk.common.predicate.symmetry.AntiSymmetricPredicate;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AsymmetricViolationFinder implements BiFunction<Relation, RelationProperty, RelationPropertyViolation> {

    private final Predicate<Relation> antiReflexivePredicate = new AntiReflexivePredicate();
    private final Predicate<Relation> antiSymmetricPredicate = new AntiSymmetricPredicate();

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final Relation relation) {

        return Stream.of(checkAntiReflexiveViolation(relation), checkAntiSymmetricViolation(relation))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<String> checkAntiReflexiveViolation(final Relation relation) {

        return Optional.of(antiReflexivePredicate.test(relation))
                .filter(Boolean.FALSE::equals)
                .map(result -> buildViolationMessage(RelationProperty.ANTIREFLEXIVITY.toString()));
    }

    private Optional<String> checkAntiSymmetricViolation(final Relation relation) {

        return Optional.of(antiSymmetricPredicate.test(relation))
                .filter(Boolean.FALSE::equals)
                .map(result -> buildViolationMessage(RelationProperty.ANTISYMMETRY.toString()));
    }

    private String buildViolationMessage(final String propertyName) {

        return "Relation does not have " + propertyName + " property";
    }
}
