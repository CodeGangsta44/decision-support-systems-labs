package edu.kpi.ip71.dovhopoliuk.strategy.violation.finder.symmetry;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.entity.RelationPropertyViolation;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.INTEGER_ZERO;

public class AntiSymmetricViolationFinder implements BiFunction<Relation, RelationProperty,  RelationPropertyViolation> {

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize() - INTEGER_ONE)
                .boxed()
                .flatMap(rowIndex -> IntStream.range(rowIndex + INTEGER_ONE, relation.getSize())
                        .filter(columnIndex -> !isSymmetricElementsNotEquals(relation, columnIndex, rowIndex))
                        .mapToObj(columnIndex -> buildViolationMessage(rowIndex, columnIndex, relation)))
                .collect(Collectors.toList());
    }

    private boolean isSymmetricElementsNotEquals(final Relation relation, final int rowIndex, final int columnIndex) {

        return !(relation.getElement(rowIndex, columnIndex)
                &&
                relation.getElement(columnIndex, rowIndex));
    }

    private String buildViolationMessage(final int rowIndex, final int columnIndex, final Relation relation) {

        return "R(" + relation.getElementName(rowIndex) + ", " + relation.getElementName(columnIndex) + ")=" + relation.getElement(rowIndex, columnIndex)
                + " == "
                + "R(" + relation.getElementName(columnIndex) + ", " + relation.getElementName(rowIndex) + ")=" + relation.getElement(columnIndex, rowIndex);
    }
}