package edu.kpi.ip71.dovhopoliuk.cp5.topsis.strategy.optimizations.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.TopsisInfo;
import edu.kpi.ip71.dovhopoliuk.cp5.topsis.strategy.optimizations.OptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp5.utils.PrintUtils;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class TopsisOptimizationStrategy implements OptimizationStrategy {


    @FunctionalInterface
    public interface TriFunction<D, I, C, K> {

        K apply(D d, I i, C c);
    }

    private final boolean onlyMaximize;

    public TopsisOptimizationStrategy(final boolean onlyMaximize) {

        this.onlyMaximize = onlyMaximize;
    }

    @Override
    public String optimize(final TopsisInfo topsisInfo) {

        PrintUtils.printTitleAndIntegerMatrix("Initial marks:", topsisInfo.getMarks());

        final List<List<Double>> normalizedMarks = onlyMaximize ? normalizeOnlyForMaximize(topsisInfo) : normalizeMarks(topsisInfo);
        PrintUtils.printTitleAndDoubleMatrix("Normalized marks:", normalizedMarks);

        final List<List<Double>> weightedMarks = weighMarks(normalizedMarks, topsisInfo);
        PrintUtils.printTitleAndDoubleMatrix("Weighted marks:", weightedMarks);

        final List<Double> pis = findPis(weightedMarks, topsisInfo);
        PrintUtils.printTitleAndDoubleList("PIS:", pis);

        final List<Double> nis = findNis(weightedMarks, topsisInfo);
        PrintUtils.printTitleAndDoubleList("NIS:", nis);

        final Map<Integer, Double> pisDistances = calculateIdealPointDistance(weightedMarks, pis);
        PrintUtils.printTitleAndMap("Distances to PIS:", pisDistances);

        final Map<Integer, Double> nisDistances = calculateIdealPointDistance(weightedMarks, nis);
        PrintUtils.printTitleAndMap("Distances to NIS:", nisDistances);

        final Map<Integer, Double> proximityToPis = calculateProximityToPis(pisDistances, nisDistances);
        PrintUtils.printTitleAndMap("Proximity to PIS:", proximityToPis);

        final List<Integer> result = performRanking(proximityToPis);
        PrintUtils.printTitleAndIntegerList("Result ranking:", result.stream().map(value -> value + INTEGER_ONE).collect(Collectors.toList()));


        return result.toString();
    }

    private List<List<Double>> normalizeOnlyForMaximize(final TopsisInfo topsisInfo) {

        final Map<Integer, Double> sumOfSquaresForCriteria = calculateSumOfSquaresForCriteria(topsisInfo);

        return IntStream.range(INTEGER_ZERO, topsisInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, topsisInfo.getQuantityOfCriteria())
                        .mapToObj(columnIndex -> topsisInfo.getMark(rowIndex, columnIndex) / sumOfSquaresForCriteria.get(columnIndex))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private Map<Integer, Double> calculateSumOfSquaresForCriteria(final TopsisInfo topsisInfo) {

        return IntStream.range(INTEGER_ZERO, topsisInfo.getQuantityOfCriteria())
                .mapToObj(index -> Map.entry(index, Math.sqrt(topsisInfo.getMarks().stream()
                        .mapToDouble(row -> row.get(index) * row.get(index))
                        .sum())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<List<Double>> normalizeMarks(final TopsisInfo topsisInfo) {

        final Map<Integer, DoubleSummaryStatistics> criteriaStatistic = collectCriteriaStatistic(topsisInfo);

        return IntStream.range(INTEGER_ZERO, topsisInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, topsisInfo.getQuantityOfCriteria())
                        .mapToObj(columnIndex -> getNormalizationFunction(topsisInfo, columnIndex)
                                .apply(topsisInfo.getMark(rowIndex, columnIndex), criteriaStatistic.get(columnIndex).getMax(), criteriaStatistic.get(columnIndex).getMin()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private Map<Integer, DoubleSummaryStatistics> collectCriteriaStatistic(final TopsisInfo topsisInfo) {

        return IntStream.range(INTEGER_ZERO, topsisInfo.getQuantityOfCriteria())
                .mapToObj(index -> Map.entry(index, topsisInfo.getMarks().stream()
                        .mapToDouble(row -> row.get(index))
                        .summaryStatistics()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    private TriFunction<Double, Double, Double, Double> getNormalizationFunction(final TopsisInfo topsisInfo, final int criteriaIndex) {

        return topsisInfo.getCriteriaToMaximize().contains(criteriaIndex) ? this::normalizeForMaximizeTarget : this::normalizeForMinimizeTarget;
    }

    private Double normalizeForMaximizeTarget(final double currentValue, final double bestValue, final double worstValue) {

        return (currentValue - worstValue) / (bestValue - worstValue);
    }

    private Double normalizeForMinimizeTarget(final double currentValue, final double bestValue, final double worstValue) {

        return (bestValue - currentValue) / (bestValue - worstValue);
    }

    private List<List<Double>> weighMarks(final List<List<Double>> normalizedMarks, final TopsisInfo topsisInfo) {

        return IntStream.range(INTEGER_ZERO, topsisInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, topsisInfo.getQuantityOfCriteria())
                        .mapToObj(columnIndex -> normalizedMarks.get(rowIndex).get(columnIndex) * topsisInfo.getWeights().get(columnIndex))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<Double> findPis(final List<List<Double>> weightedMarks, final TopsisInfo topsisInfo) {

        return findIdealPoint(weightedMarks, topsisInfo, this::getPisValueForIndex);
    }

    private Double getPisValueForIndex(final int index, final List<List<Double>> weightedMarks, final TopsisInfo topsisInfo) {

        final DoubleSummaryStatistics doubleSummaryStatistics = weightedMarks.stream()
                .mapToDouble(row -> row.get(index))
                .summaryStatistics();

        return onlyMaximize || topsisInfo.getCriteriaToMaximize().contains(index) ? doubleSummaryStatistics.getMax() : doubleSummaryStatistics.getMin();
    }

    private List<Double> findNis(final List<List<Double>> weightedMarks, final TopsisInfo topsisInfo) {

        return findIdealPoint(weightedMarks, topsisInfo, this::getNisValueForIndex);
    }

    private Double getNisValueForIndex(final int index, final List<List<Double>> weightedMarks, final TopsisInfo topsisInfo) {

        final DoubleSummaryStatistics doubleSummaryStatistics = weightedMarks.stream()
                .mapToDouble(row -> row.get(index))
                .summaryStatistics();

        return onlyMaximize || topsisInfo.getCriteriaToMaximize().contains(index) ? doubleSummaryStatistics.getMin() : doubleSummaryStatistics.getMax();
    }

    private List<Double> findIdealPoint(final List<List<Double>> weightedMarks, final TopsisInfo topsisInfo, TriFunction<Integer, List<List<Double>>, TopsisInfo, Double> valueFunction) {

        return IntStream.range(INTEGER_ZERO, topsisInfo.getQuantityOfCriteria())
                .mapToObj(index -> valueFunction.apply(index, weightedMarks, topsisInfo))
                .collect(Collectors.toList());
    }

    private Map<Integer, Double> calculateIdealPointDistance(final List<List<Double>> weightedMarks, final List<Double> pis) {

        return IntStream.range(INTEGER_ZERO, weightedMarks.size())
                .mapToObj(index -> Map.entry(index, calculateGeometricalDistance(weightedMarks.get(index), pis)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Double calculateGeometricalDistance(final List<Double> firstPoint, final List<Double> secondPoint) {

        return Math.sqrt(IntStream.range(INTEGER_ZERO, firstPoint.size())
                .mapToDouble(index -> Math.pow(firstPoint.get(index) - secondPoint.get(index), 2))
                .sum());
    }

    private Map<Integer, Double> calculateProximityToPis(final Map<Integer, Double> pisDistances, final Map<Integer, Double> nisDistances) {

        return IntStream.range(INTEGER_ZERO, pisDistances.size())
                .mapToObj(index -> Map.entry(index, nisDistances.get(index) / (pisDistances.get(index) + nisDistances.get(index))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> performRanking(final Map<Integer, Double> proximityToPis) {

        return proximityToPis.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
