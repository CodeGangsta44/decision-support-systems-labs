package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.common.controller.WriteOutputController;
import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.impl.DominationAndBlockingSolvingStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.impl.NeumannMorgensternAndKSolvingStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.solving.impl.DefaultSolvingStrategy;

import java.util.List;

public class Main {

    public static void main(final String... args) {

        final CriteriaInfo criteriaInfo = new ReadInputController().readCriteriaInput("cp3/input.txt");

        final List<Relation> relations = new DefaultSolvingStrategy().solve(criteriaInfo);

        new WriteOutputController().saveRelations("output/cp3/output.txt", relations);

        final DominationAndBlockingSolvingStrategy dominationAndBlockingSolvingStrategy = new DominationAndBlockingSolvingStrategy();
        final NeumannMorgensternAndKSolvingStrategy neumannMorgensternAndKSolvingStrategy = new NeumannMorgensternAndKSolvingStrategy();

        relations.forEach(relation -> {

            final String dominationAndBlockingResult = dominationAndBlockingSolvingStrategy.solve(relation);
            final String neumannMorgensternAndKResult = neumannMorgensternAndKSolvingStrategy.solve(relation);

            relation.print();

            System.out.println("\nDomination and blocking optimization:");
            System.out.println(dominationAndBlockingResult);
            System.out.println(neumannMorgensternAndKResult);
        });
    }
}
