package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.population.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.population.ElectrePopulationStrategy;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ElectreConcordanceDiscordancePopulationStrategy implements ElectrePopulationStrategy {

    @Override
    public void populate(final ElectreInfo electreInfo) {

        populateConcordanceMatrix(electreInfo);
        populateDiscordanceMatrix(electreInfo);
    }

    @FunctionalInterface
    public interface TriFunction<T, U, R, N> {

        N apply(T t, U u, R r);
    }

    private void populateConcordanceMatrix(final ElectreInfo electreInfo) {

        electreInfo.setConcordanceMatrix(calculateIndexMatrix(electreInfo, this::getCellConcordanceIndex));
    }

    private void populateDiscordanceMatrix(final ElectreInfo electreInfo) {

        electreInfo.setDiscordanceMatrix(calculateIndexMatrix(electreInfo, this::getCellDiscordanceIndex));
    }

    private List<List<Double>> calculateIndexMatrix(final ElectreInfo electreInfo, TriFunction<Integer, Integer, ElectreInfo, Double> cellValueFunction) {

        return IntStream.range(INTEGER_ZERO, electreInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, electreInfo.getSize())
                        .mapToObj(columnIndex -> cellValueFunction.apply(rowIndex, columnIndex, electreInfo))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private double getCellConcordanceIndex(final int rowIndex, final int columnIndex, final ElectreInfo electreInfo) {

        return Optional.of(rowIndex)
                .filter(index -> index != columnIndex)
                .map(index -> calculateCellConcordanceIndex(rowIndex, columnIndex, electreInfo))
                .orElse(DOUBLE_ZERO);
    }

    private double calculateCellConcordanceIndex(final int rowIndex, final int columnIndex, final ElectreInfo electreInfo) {

        final double numerator = IntStream.range(INTEGER_ZERO, electreInfo.getWeights().size())
                .filter(index -> electreInfo.getMark(rowIndex, index) >= electreInfo.getMark(columnIndex, index))
                .mapToDouble(electreInfo::getWeight)
                .sum();

        final double denominator = IntStream.range(INTEGER_ZERO, electreInfo.getWeights().size())
                .mapToDouble(electreInfo::getWeight)
                .sum();

        return numerator / denominator;
    }

    private double getCellDiscordanceIndex(final int rowIndex, final int columnIndex, final ElectreInfo electreInfo) {

        return Optional.of(rowIndex)
                .filter(index -> index != columnIndex)
                .map(index -> calculateCellDiscordanceIndex(rowIndex, columnIndex, electreInfo))
                .orElse(DOUBLE_ONE);
    }

    private double calculateCellDiscordanceIndex(final int rowIndex, final int columnIndex, final ElectreInfo electreInfo) {

        final OptionalDouble numerator = IntStream.range(INTEGER_ZERO, electreInfo.getWeights().size())
                .filter(index -> electreInfo.getMark(rowIndex, index) < electreInfo.getMark(columnIndex, index))
                .mapToDouble(index -> electreInfo.getWeight(index) * (electreInfo.getMark(columnIndex, index) - electreInfo.getMark(rowIndex, index)))
                .max();

        final OptionalDouble denominator = IntStream.range(INTEGER_ZERO, electreInfo.getWeights().size())
                .filter(index -> electreInfo.getMark(rowIndex, index) < electreInfo.getMark(columnIndex, index))
                .mapToDouble(index -> electreInfo.getWeight(index) * getMarksRange(index, electreInfo))
                .max();

        return numerator.orElse(DOUBLE_ZERO) / denominator.orElse(DOUBLE_ONE);
    }

    private double getMarksRange(final int columnIndex, final ElectreInfo electreInfo) {

        return Optional.of(
                IntStream.range(INTEGER_ZERO, electreInfo.getSize())
                        .mapToDouble(rowIndex -> electreInfo.getMark(rowIndex, columnIndex))
                        .summaryStatistics())
                .map(statistics -> statistics.getMax() - statistics.getMin())
                .orElse(DOUBLE_ZERO);
    }
}
