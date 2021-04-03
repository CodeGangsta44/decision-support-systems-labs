package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.utils;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class FuzzyRelationOperationUtils {

    public static FuzzyRelation union(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return FuzzyRelation.builder()
                .matrix(createUnionMatrix(relation1, relation2))
                .elements(copyElements(relation1))
                .relationName("Union " + relation1.getRelationName() + " " + relation2.getRelationName())
                .build();
    }

    public static FuzzyRelation intersect(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return FuzzyRelation.builder()
                .matrix(createIntersectMatrix(relation1, relation2))
                .elements(copyElements(relation1))
                .relationName("Intersection " + relation1.getRelationName() + " " + relation2.getRelationName())
                .build();
    }

    public static FuzzyRelation complement(final FuzzyRelation relation1) {

        return FuzzyRelation.builder()
                .matrix(createComplementMatrix(relation1))
                .elements(copyElements(relation1))
                .relationName("Complement " + relation1.getRelationName())
                .build();
    }

    public static FuzzyRelation composition(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return FuzzyRelation.builder()
                .matrix(createCompositionMatrix(relation1, relation2))
                .elements(copyElements(relation1))
                .relationName("Composition " + relation1.getRelationName() + " " + relation2.getRelationName())
                .build();
    }

    public static FuzzyRelation buildAlphaLevel(final FuzzyRelation relation1, final double alpha) {

        return FuzzyRelation.builder()
                .matrix(createAlphaLevelMatrix(relation1, alpha))
                .elements(copyElements(relation1))
                .relationName("Alpha level " + alpha + " of " + relation1.getRelationName())
                .build();
    }

    public static FuzzyRelation buildStrictAdvantage(final FuzzyRelation relation1) {

        return FuzzyRelation.builder()
                .matrix(createStrictAdvantageMatrix(relation1))
                .elements(copyElements(relation1))
                .relationName("Strict advantage of " + relation1.getRelationName())
                .build();
    }

    private static List<List<Double>> createUnionMatrix(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation1.getSize())
                        .mapToObj(columnIndex -> Math.max(relation1.getElement(rowIndex, columnIndex), relation2.getElement(rowIndex, columnIndex)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<List<Double>> createIntersectMatrix(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation1.getSize())
                        .mapToObj(columnIndex -> Math.min(relation1.getElement(rowIndex, columnIndex), relation2.getElement(rowIndex, columnIndex)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<List<Double>> createComplementMatrix(final FuzzyRelation relation1) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation1.getSize())
                        .mapToObj(columnIndex -> DOUBLE_ONE - relation1.getElement(rowIndex, columnIndex))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<List<Double>> createCompositionMatrix(final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation1.getSize())
                        .mapToObj(columnIndex -> getCompositionCellValue(rowIndex, columnIndex, relation1, relation2))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static Double getCompositionCellValue(final int rowIndex, final int columnIndex, final FuzzyRelation relation1, final FuzzyRelation relation2) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToDouble(index -> Math.min(relation1.getElement(rowIndex, index), relation2.getElement(index, columnIndex)))
                .max()
                .orElse(DOUBLE_ZERO);
    }

    private static List<List<Double>> createAlphaLevelMatrix(final FuzzyRelation relation1, final double alpha) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation1.getSize())
                        .mapToObj(columnIndex -> relation1.getElement(rowIndex, columnIndex) >= alpha ? DOUBLE_ONE : DOUBLE_ZERO)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<List<Double>> createStrictAdvantageMatrix(final FuzzyRelation relation1) {

        return IntStream.range(INTEGER_ZERO, relation1.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation1.getSize())
                        .mapToObj(columnIndex -> Math.max(DOUBLE_ZERO, relation1.getElement(rowIndex, columnIndex) - relation1.getElement(columnIndex, rowIndex)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<String> copyElements(final FuzzyRelation relation) {

        return relation.getElements().stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}
