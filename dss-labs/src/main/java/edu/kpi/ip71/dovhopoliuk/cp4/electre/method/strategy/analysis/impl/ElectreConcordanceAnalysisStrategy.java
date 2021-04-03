package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.chart.LineChartEx;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.AnalysisStrategy;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;

public class ElectreConcordanceAnalysisStrategy extends AbstractElectreAnalysisStrategy implements AnalysisStrategy {

    private static final double CONSTANT_D = 0.49;
    private static final double SEED = 0.5;
    private static final double ITERATION_STEP = 0.01;

    private static final String Y_AXIS_LABEL = "Кількість альтернатив у ядрі";
    private static final String X_AXIS_LABEL = "Значення порогового індексу узгодження";
    private static final String TITLE = "Залежність розміру ядра від порогового індексу узгодження";

    public ElectreConcordanceAnalysisStrategy() {

        super();
    }

    @Override
    public void analyze(final ElectreInfo electreInfo) {

        new LineChartEx(createDataSet(SEED, value -> value <= DOUBLE_ONE, value -> value + ITERATION_STEP, electreInfo),
                Y_AXIS_LABEL, X_AXIS_LABEL, TITLE)
                .setVisible(Boolean.TRUE);
    }

    @Override
    protected void populateParameters(final double value, final ElectreInfo electreInfo) {

        electreInfo.setC(value);
        electreInfo.setD(CONSTANT_D);
    }
}
