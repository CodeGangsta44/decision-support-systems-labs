package edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.optimization.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.VikorInfo;
import edu.kpi.ip71.dovhopoliuk.cp5.utils.PrintUtils;
import edu.kpi.ip71.dovhopoliuk.cp5.vikor.strategy.optimization.OptimizationStrategy;

import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class VikorOptimizationStrategy implements OptimizationStrategy {

    @Override
    public String optimize(final VikorInfo vikorInfo) {

        return optimizeForWeightedMarks(vikorInfo, getWeightedMarks(vikorInfo));
    }

    @Override
    public List<List<Double>> getWeightedMarks(final VikorInfo vikorInfo) {

        normalizeWeights(vikorInfo);

        PrintUtils.printTitleAndIntegerMatrix("Initial marks:", vikorInfo.getMarks());

        final List<List<Double>> normalizedMarks = normalizeMarks(vikorInfo);
        PrintUtils.printTitleAndDoubleMatrix("Normalized marks:", normalizedMarks);

        final List<List<Double>> weightedMarks = weighMarks(normalizedMarks, vikorInfo);
        PrintUtils.printTitleAndDoubleMatrix("Weighted marks:", weightedMarks);

        return weightedMarks;
    }

    @Override
    public String optimizeForWeightedMarks(final VikorInfo vikorInfo, final List<List<Double>> weightedMarks) {

        final Map<Integer, Double> sMap = calculateS(weightedMarks);
        PrintUtils.printTitleAndMap("S:", sMap);
        final Map<Integer, Double> rMap = calculateR(weightedMarks);
        PrintUtils.printTitleAndMap("R:", rMap);
        final Map<Integer, Double> qMap = calculateQ(sMap, rMap, vikorInfo);
        PrintUtils.printTitleAndMap("Q:", qMap);

        System.out.println();
        final List<Map.Entry<Integer, Double>> sList = performRanking(sMap);
        PrintUtils.printTitleAndListOfEntries("Ranked S:", sList);
        final List<Map.Entry<Integer, Double>> rList = performRanking(rMap);
        PrintUtils.printTitleAndListOfEntries("Ranked R:", rList);
        final List<Map.Entry<Integer, Double>> qList = performRanking(qMap);
        PrintUtils.printTitleAndListOfEntries("Ranked Q:", qList);
        System.out.println();

        PrintUtils.printTitleAndIntegerList("Ranked alternatives:", qList.stream().map(entry -> entry.getKey() + INTEGER_ONE).collect(Collectors.toList()));

        final List<Integer> result = getResult(sList, rList, qList);
        PrintUtils.printTitleAndIntegerList("Compromise solution:", result.stream().map(value -> value + INTEGER_ONE).collect(Collectors.toList()));

        return result.toString();
    }

    private void normalizeWeights(final VikorInfo vikorInfo) {

        final double sumOfWeight = vikorInfo.getWeights().stream().mapToDouble(value -> value).sum();

        final List<Double> normalizedWeights = IntStream.range(INTEGER_ZERO, vikorInfo.getQuantityOfCriteria())
                .mapToObj(index -> vikorInfo.getWeights().get(index) / sumOfWeight)
                .collect(Collectors.toList());

        vikorInfo.setWeights(normalizedWeights);
    }

    private List<List<Double>> normalizeMarks(final VikorInfo vikorInfo) {

        final Map<Integer, DoubleSummaryStatistics> criteriaStatistic = collectCriteriaStatistic(vikorInfo);

        return IntStream.range(INTEGER_ZERO, vikorInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, vikorInfo.getQuantityOfCriteria())
                        .mapToObj(columnIndex -> normalizeValue(vikorInfo.getMark(rowIndex, columnIndex), criteriaStatistic.get(columnIndex).getMax(), criteriaStatistic.get(columnIndex).getMin()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private Map<Integer, DoubleSummaryStatistics> collectCriteriaStatistic(final VikorInfo vikorInfo) {

        return IntStream.range(INTEGER_ZERO, vikorInfo.getQuantityOfCriteria())
                .mapToObj(index -> Map.entry(index, vikorInfo.getMarks().stream()
                        .mapToDouble(row -> row.get(index))
                        .summaryStatistics()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    private Double normalizeValue(final double currentValue, final double bestValue, final double worstValue) {

        return (bestValue - currentValue) / (bestValue - worstValue);
    }

    private List<List<Double>> weighMarks(final List<List<Double>> normalizedMarks, final VikorInfo vikorInfo) {

        return IntStream.range(INTEGER_ZERO, vikorInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, vikorInfo.getQuantityOfCriteria())
                        .mapToObj(columnIndex -> normalizedMarks.get(rowIndex).get(columnIndex) * vikorInfo.getWeights().get(columnIndex))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private Map<Integer, Double> calculateS(final List<List<Double>> weightedMarks) {

        return IntStream.range(INTEGER_ZERO, weightedMarks.size())
                .mapToObj(index -> Map.entry(index, weightedMarks.get(index).stream().mapToDouble(value -> value).sum()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, Double> calculateR(final List<List<Double>> weightedMarks) {

        return IntStream.range(INTEGER_ZERO, weightedMarks.size())
                .mapToObj(index -> Map.entry(index, weightedMarks.get(index).stream().mapToDouble(value -> value).max().orElse(DOUBLE_ZERO)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, Double> calculateQ(final Map<Integer, Double> sMap, final Map<Integer, Double> rMap, final VikorInfo vikorInfo) {

        final DoubleSummaryStatistics sStatistics = sMap.values().stream().mapToDouble(value -> value).summaryStatistics();
        final DoubleSummaryStatistics rStatistics = rMap.values().stream().mapToDouble(value -> value).summaryStatistics();

        if (sStatistics.getMin() == DOUBLE_ZERO
                && sStatistics.getMax() == DOUBLE_ONE
                && rStatistics.getMin() == DOUBLE_ZERO
                && rStatistics.getMax() == DOUBLE_ONE) {

            return IntStream.range(INTEGER_ZERO, vikorInfo.getSize())
                    .mapToObj(index -> Map.entry(index, vikorInfo.getV() * sMap.get(index) + (INTEGER_ONE - vikorInfo.getV()) * rMap.get(index)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        return IntStream.range(INTEGER_ZERO, vikorInfo.getSize())
                .mapToObj(index -> Map.entry(index, calculateQValue(sStatistics, rStatistics, sMap.get(index), rMap.get(index), vikorInfo.getV())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Double calculateQValue(final DoubleSummaryStatistics sStatistics, final DoubleSummaryStatistics rStatistics,
                                   final double s, final double r, final double v) {

        return (v * (s - sStatistics.getMin()) / (sStatistics.getMax() - sStatistics.getMin()))
                + ((INTEGER_ONE - v) * (r - rStatistics.getMin()) / (rStatistics.getMax() - rStatistics.getMin()));
    }

    private List<Map.Entry<Integer, Double>> performRanking(final Map<Integer, Double> map) {

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }

    private boolean isAcceptableAdvantage(final List<Map.Entry<Integer, Double>> qList) {

        return (qList.get(INTEGER_ONE).getValue() - qList.get(INTEGER_ZERO).getValue()) >= (DOUBLE_ONE / (qList.size() - DOUBLE_ONE));
    }

    private boolean isAcceptableStability(final List<Map.Entry<Integer, Double>> sList, final List<Map.Entry<Integer, Double>> rList, final List<Map.Entry<Integer, Double>> qList) {

        return qList.get(INTEGER_ZERO).getKey().equals(sList.get(INTEGER_ZERO).getKey())
                || qList.get(INTEGER_ZERO).getKey().equals(rList.get(INTEGER_ZERO).getKey());
    }

    private List<Integer> getResult(final List<Map.Entry<Integer, Double>> sList, final List<Map.Entry<Integer, Double>> rList, final List<Map.Entry<Integer, Double>> qList) {

        if (isAcceptableAdvantage(qList) && isAcceptableStability(sList, rList, qList)) {

            return Collections.singletonList(qList.get(INTEGER_ZERO).getKey());

        } else if (isAcceptableAdvantage(qList) && !isAcceptableStability(sList, rList, qList)) {

            return List.of(qList.get(INTEGER_ZERO).getKey(), qList.get(INTEGER_ONE).getKey());

        } else {

            final double acceptableDiff = (DOUBLE_ONE / (qList.size() - DOUBLE_ONE));
            final double valueOfFirst = qList.get(INTEGER_ZERO).getValue();

            return qList.stream()
                    .filter(entry -> entry.getValue() - valueOfFirst < acceptableDiff)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }
}
