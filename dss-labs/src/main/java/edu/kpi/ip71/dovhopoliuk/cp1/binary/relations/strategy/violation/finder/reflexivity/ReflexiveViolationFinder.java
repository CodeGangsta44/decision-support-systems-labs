package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.reflexivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationPropertyViolation;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ReflexiveViolationFinder implements BiFunction<Relation, RelationProperty,  RelationPropertyViolation> {

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .filter(index -> !Boolean.TRUE.equals(relation.getElement(index, index)))
                .mapToObj(index -> buildViolationMessage(index, relation))
                .collect(Collectors.toList());
    }

    private String buildViolationMessage(final int index, final Relation relation) {

        return "R(" + relation.getElementName(index) + ", " + relation.getElementName(index) + ") is " + relation.getElement(index, index);
    }
}
