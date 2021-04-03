package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.RelationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.nm.RelationNeumannMorgensternOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation.RelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation.impl.ElectreRelationDerivationStrategy;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public abstract class AbstractElectreAnalysisStrategy {

    protected static final String LINE_NAME = "Залежність";

    private final RelationOptimizationStrategy relationOptimizationStrategy;
    private final RelationDerivationStrategy relationDerivationStrategy;

    public AbstractElectreAnalysisStrategy() {

        this.relationOptimizationStrategy = new RelationNeumannMorgensternOptimizationStrategy();
        this.relationDerivationStrategy = new ElectreRelationDerivationStrategy();
    }

    protected XYDataset createDataSet(final double seed, final Predicate<Double> iterationPredicate,
                                    final UnaryOperator<Double> iterationFunction, final ElectreInfo electreInfo) {

        XYSeries series = new XYSeries(LINE_NAME);

        Stream.iterate(seed, iterationPredicate, iterationFunction)
                .map(value -> Math.round(value * 100.0) / 100.0)
                .forEach(value -> series.add(value, (Number) getCoreSizeForIndexes(value, electreInfo)));

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    protected int getCoreSizeForIndexes(final double value, final ElectreInfo electreInfo) {

        populateParameters(value, electreInfo);

        return Optional.ofNullable(relationDerivationStrategy.derivate(electreInfo))
                .filter(RelationProperty.ACYCLITY)
                .map(relationOptimizationStrategy::getOptimizedResultAsSet)
                .map(Collection::size)
                .orElse(INTEGER_ZERO);

    }

    protected abstract void populateParameters(final double value, final ElectreInfo electreInfo);
}
