package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation.finder.transitivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelationPropertyViolation;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class TransitivityViolationFinder implements BiFunction<FuzzyRelation, FuzzyRelationProperty, FuzzyRelationPropertyViolation> {

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
                .flatMap(xIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                        .boxed()
                        .flatMap(yIndex -> IntStream.range(INTEGER_ZERO, fuzzyRelation.getSize())
                                .filter(zIndex -> fuzzyRelation.getElement(xIndex, zIndex) <
                                        Math.min(fuzzyRelation.getElement(xIndex, yIndex), fuzzyRelation.getElement(yIndex, zIndex)))
                                .mapToObj(zIndex -> buildViolationMessage(xIndex, yIndex, zIndex, fuzzyRelation))))
                .collect(Collectors.toList());
    }

    private String buildViolationMessage(final int xIndex, final int yIndex, final int zIndex, final FuzzyRelation fuzzyRelation) {

        return "Transitive violations: x=" + fuzzyRelation.getElementName(xIndex) + ", y=" + fuzzyRelation.getElementName(yIndex) + ", z=" + fuzzyRelation.getElementName(zIndex);
    }
}
