package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.chart.LineChartEx;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.AnalysisStrategy;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ZERO;

public class ElectreConcordanceAndDiscordanceAnalysisStrategy extends AbstractElectreAnalysisStrategy implements AnalysisStrategy {

    private static final double SEED = DOUBLE_ZERO;
    private static final double ITERATION_STEP = 0.01;

    private static final String Y_AXIS_LABEL = "Кількість альтернатив у ядрі";
    private static final String X_AXIS_LABEL = "Різниця між порговими значеннями індексів узгодження та неузгодження";
    private static final String TITLE = "Залежність розміру ядра від різниці значень індексів";

    @Override
    public void analyze(ElectreInfo electreInfo) {

        new LineChartEx(createDataSet(SEED, value -> value <= DOUBLE_ONE, value -> value + ITERATION_STEP, electreInfo),
                Y_AXIS_LABEL, X_AXIS_LABEL, TITLE)
                .setVisible(Boolean.TRUE);
    }


    @Override
    protected void populateParameters(final double value, final ElectreInfo electreInfo) {

        final double diff = (DOUBLE_ONE - value) / 2;
        electreInfo.setC(DOUBLE_ONE - diff);
        electreInfo.setD(DOUBLE_ZERO + diff);
    }
}
