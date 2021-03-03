package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.transitivity;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationPropertyViolation;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.constants.Constants.INTEGER_ZERO;

public class TransitiveViolationFinder implements BiFunction<Relation, RelationProperty, RelationPropertyViolation> {

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .boxed()
                .flatMap(rowIndex -> checkTransitivityConditionForRow(relation, rowIndex))
                .collect(Collectors.toList());
    }

    private Stream<String> checkTransitivityConditionForRow(final Relation relation, final int indexToCheck) {

        return relation.getLowerSection(indexToCheck).stream()
                .flatMap(rowIndex -> IntStream.range(INTEGER_ZERO, relation.getSize())
                        .filter(columnIndex -> relation.getElement(rowIndex, columnIndex))
                        .filter(columnIndex -> !relation.getElement(indexToCheck, columnIndex))
                        .mapToObj(columnIndex -> buildViolationMessage(relation, indexToCheck, rowIndex, columnIndex)));
    }

    private String buildViolationMessage(final Relation relation, final int firstIndex, final int secondIndex, final int thirdIndex) {

        return "On relation exists " + relation.getElementName(firstIndex)
                + "->" + relation.getElementName(secondIndex)
                + "->" + relation.getElementName(thirdIndex)
                + " path, but does not exist "
                + relation.getElementName(firstIndex)
                + "->" + relation.getElementName(thirdIndex)
                + " path";
    }
}
