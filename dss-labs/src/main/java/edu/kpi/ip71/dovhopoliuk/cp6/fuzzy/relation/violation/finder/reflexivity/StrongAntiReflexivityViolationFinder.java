package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.reflexivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelationPropertyViolation;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class StrongAntiReflexivityViolationFinder implements BiFunction<FuzzyRelation, FuzzyRelationProperty, FuzzyRelationPropertyViolation> {

    @Override
    public FuzzyRelationPropertyViolation apply(final FuzzyRelation relation, FuzzyRelationProperty property) {

        return FuzzyRelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final FuzzyRelation fuzzyRelation) {

        return IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                .boxed()
                .flatMap(rowIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                        .filter(columnIndex -> columnIndex != rowIndex)
                        .filter(columnIndex -> fuzzyRelation.getElement(rowIndex, columnIndex) <= DOUBLE_ZERO)
                        .mapToObj(columnIndex -> buildViolationMessage(rowIndex, columnIndex, fuzzyRelation)))
                .collect(Collectors.toList());
    }

    private String buildViolationMessage(final int rowIndex, final int columnIndex, final FuzzyRelation fuzzyRelation) {

        return "R(" + fuzzyRelation.getElementName(rowIndex) + ", " + fuzzyRelation.getElementName(columnIndex) + ") is " + fuzzyRelation.getElement(rowIndex, columnIndex);
    }
}
