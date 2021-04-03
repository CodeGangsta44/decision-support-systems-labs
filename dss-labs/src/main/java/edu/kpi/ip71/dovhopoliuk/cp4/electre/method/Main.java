package edu.kpi.ip71.dovhopoliuk.cp4.electre.method;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.common.controller.WriteOutputController;
import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl.ElectreConcordanceAnalysisStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl.ElectreConcordanceAndDiscordanceAnalysisStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.analysis.impl.ElectreDiscordanceAnalysisStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.solving.impl.DefaultSolvingStrategy;

public class Main {

    public static void main(final String... args) {

        final ElectreInfo electreInfo = new ReadInputController().readElectreInput("cp4/input.txt");

        final String result = new DefaultSolvingStrategy().solve(electreInfo);

        new WriteOutputController().saveResultString("output/cp4/output.txt", result);

        new ElectreConcordanceAnalysisStrategy().analyze(electreInfo);
        new ElectreDiscordanceAnalysisStrategy().analyze(electreInfo);
        new ElectreConcordanceAndDiscordanceAnalysisStrategy().analyze(electreInfo);
    }
}
