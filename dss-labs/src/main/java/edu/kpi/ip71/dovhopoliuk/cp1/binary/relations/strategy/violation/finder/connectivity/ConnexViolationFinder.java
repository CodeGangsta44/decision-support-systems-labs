package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.connectivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationPropertyViolation;
import edu.kpi.ip71.dovhopoliuk.common.predicate.reflexivity.ReflexivePredicate;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ConnexViolationFinder implements BiFunction<Relation, RelationProperty,  RelationPropertyViolation> {

    private final Predicate<Relation> reflexivePredicate = new ReflexivePredicate();

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final Relation relation) {

        return Stream.concat(findSymmetricZerosViolations(relation), checkReflexiveViolation(relation).stream())
                .collect(Collectors.toList());
    }

    private Optional<String> checkReflexiveViolation(final Relation relation) {

        return Optional.of(reflexivePredicate.test(relation))
                .filter(Boolean.FALSE::equals)
                .map(result -> buildPropertyViolationMessage(RelationProperty.REFLEXIVITY.toString()));
    }

    private Stream<String> findSymmetricZerosViolations(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize() - INTEGER_ONE)
                .boxed()
                .flatMap(rowIndex -> IntStream.range(rowIndex + INTEGER_ONE, relation.getSize())
                        .filter(columnIndex -> isSymmetricElementsZeros(relation, rowIndex, columnIndex))
                        .mapToObj(columnIndex -> buildViolationMessage(rowIndex, columnIndex, relation)));
    }

    private boolean isSymmetricElementsZeros(final Relation relation, final int rowIndex, final int columnIndex) {

        return (!relation.getElement(rowIndex, columnIndex))
                &&
                (!relation.getElement(columnIndex, rowIndex));
    }

    private String buildViolationMessage(final int rowIndex, final int columnIndex, final Relation relation) {

        return "R(" + relation.getElementName(rowIndex) + ", " + relation.getElementName(columnIndex) + ") == "
                + "R(" + relation.getElementName(columnIndex) + ", " + relation.getElementName(rowIndex) + ") == " + relation.getElement(columnIndex, rowIndex);
    }

    private String buildPropertyViolationMessage(final String propertyName) {

        return "Relation does not have " + propertyName + " property";
    }
}
