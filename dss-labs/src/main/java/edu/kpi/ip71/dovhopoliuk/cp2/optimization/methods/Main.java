package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods;

import edu.kpi.ip71.dovhopoliuk.common.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.impl.DominationAndBlockingSolvingStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.solving.impl.NeumannMorgensternAndKSolvingStrategy;

public class Main {

    public static void main(final String... args) {

        final DominationAndBlockingSolvingStrategy dominationAndBlockingSolvingStrategy = new DominationAndBlockingSolvingStrategy();
        final NeumannMorgensternAndKSolvingStrategy neumannMorgensternAndKSolvingStrategy = new NeumannMorgensternAndKSolvingStrategy();


        System.out.println("DOMINATION AND BLOCKING OPTIMIZATION PART:");
        new ReadInputController().readInput("cp2/domination-and-blocking/input.txt").forEach(relation -> {

            final String result = dominationAndBlockingSolvingStrategy.solve(relation);
            relation.print();
            System.out.println(result);
        });

        System.out.println("\n\nNEUMANN-MORGENSTERN AND K OPTIMIZATION PART:");
        new ReadInputController().readInput("cp2/neumann-morgenstern-and-k/input.txt").forEach(relation -> {

            final String result = neumannMorgensternAndKSolvingStrategy.solve(relation);
            relation.print();
            System.out.println(result);
        });
    }
}
