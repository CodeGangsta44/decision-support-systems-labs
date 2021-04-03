package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.strategy.solving.DefaultSolvingStrategy;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.strategy.solving.ExpertSolvingStrategy;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class Main {

    public static void main(final String... args) {

        final DefaultSolvingStrategy solvingStrategy = new DefaultSolvingStrategy();
        final ExpertSolvingStrategy expertSolvingStrategy = new ExpertSolvingStrategy();

        solvingStrategy.solve(new ReadInputController().readFuzzyInput("cp6/input.txt"));

        expertSolvingStrategy.solveExpertTask(new ReadInputController().readFuzzyInput("cp6/input.txt").get(INTEGER_ZERO));
    }
}
