package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.PropertiesDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.RelationSimilarClassesChoosingStrategy;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.SolvingStrategy;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.RelationPropertyViolationFindingStrategy;

public class Main {

    public static void main(final String... args) {

        final SolvingStrategy solvingStrategy = new SolvingStrategy(new PropertiesDerivationStrategy(), new RelationSimilarClassesChoosingStrategy());

        final RelationPropertyViolationFindingStrategy relationPropertyViolationFindingStrategy =
                new RelationPropertyViolationFindingStrategy();

        new ReadInputController().readInput()
                .forEach(relation -> {

                    RelationClass relationClass = solvingStrategy.solve(relation);
                    relationPropertyViolationFindingStrategy.findViolations(relation);

                    relation.print();
                    System.out.println("\nMain Relation class: " + relationClass);
                });
    }
}
