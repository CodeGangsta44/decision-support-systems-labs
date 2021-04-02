package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.chart.LineChartEx;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.AnalysisStrategy;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;

public class ElectreDiscordanceAnalysisStrategy extends AbstractElectreAnalysisStrategy implements AnalysisStrategy {

    private static final double CONSTANT_C = 0.5;
    private static final double SEED = 0.5;
    private static final double ITERATION_STEP = 0.01;

    private static final String Y_AXIS_LABEL = "Кількість альтернатив у ядрі";
    private static final String X_AXIS_LABEL = "Значення порогового індексу неузгодження";
    private static final String TITLE = "Залежність розміру ядра від порогового індексу неузгодження";


    public ElectreDiscordanceAnalysisStrategy() {

        super();
    }

    @Override
    public void analyze(ElectreInfo electreInfo) {

        new LineChartEx(createDataSet(SEED, value -> value >= DOUBLE_ZERO, value -> value - ITERATION_STEP, electreInfo),
                Y_AXIS_LABEL, X_AXIS_LABEL, TITLE)
                .setVisible(Boolean.TRUE);
    }


    @Override
    protected void populateParameters(final double value, final ElectreInfo electreInfo) {

        electreInfo.setC(CONSTANT_C);
        electreInfo.setD(value);
    }
}
