package edu.kpi.ip71.dovhopoliuk;

import edu.kpi.ip71.dovhopoliuk.controller.ReadInputController;
import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.predicate.connectivity.ConnexPredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.cycle.AcyclicPredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.reflexivity.AntiReflexivePredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.reflexivity.ReflexivePredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.symmetry.AntiSymmetricPredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.symmetry.AsymmetricPredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.symmetry.SymmetricPredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.transitivity.NegativelyTransitivePredicate;
import edu.kpi.ip71.dovhopoliuk.predicate.transitivity.TransitivePredicate;
import edu.kpi.ip71.dovhopoliuk.strategy.PropertiesDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.strategy.SolvingStrategy;
import edu.kpi.ip71.dovhopoliuk.strategy.violation.RelationPropertyViolationFindingStrategy;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(final String... args) {

//        final List<List<Boolean>> matrix = List.of(
//                List.of(false, true, false, false, false),
//                List.of(false, false, true, false, false),
//                List.of(false, false, false, true, false),
//                List.of(false, false, false, false, true),
//                List.of(false, false, false, false, false));
//
//        final Relation relation = Relation.builder()
//                .matrix(matrix)
//                .elements(Collections.emptyList())
//                .build();
//
//        final Predicate<Relation> reflexivePredicate = new ReflexivePredicate();
//        final Predicate<Relation> antiReflexivePredicate = new AntiReflexivePredicate();
//        final Predicate<Relation> symmetricPredicate = new SymmetricPredicate();
//        final Predicate<Relation> asymmetricPredicate = new AsymmetricPredicate();
//        final Predicate<Relation> antiSymmetricPredicate = new AntiSymmetricPredicate();
//        final Predicate<Relation> transitivePredicate = new TransitivePredicate();
//        final Predicate<Relation> negativelyTransitivePredicate = new NegativelyTransitivePredicate();
//        final Predicate<Relation> connexPredicate = new ConnexPredicate();
//        final Predicate<Relation> acyclicPredicate = new AcyclicPredicate();
//
//        System.out.println("ReflexivePredicate: " + reflexivePredicate.test(relation));
//        System.out.println("AntiReflexivePredicate: " + antiReflexivePredicate.test(relation));
//        System.out.println("SymmetricPredicate: " + symmetricPredicate.test(relation));
//        System.out.println("AsymmetricPredicate: " + asymmetricPredicate.test(relation));
//        System.out.println("AntiSymmetricPredicate: " + antiSymmetricPredicate.test(relation));
//        System.out.println("TransitivePredicate: " + transitivePredicate.test(relation));
//        System.out.println("NegativelyTransitivePredicate: " + negativelyTransitivePredicate.test(relation));
//        System.out.println("ConnexPredicate: " + connexPredicate.test(relation));
//        System.out.println("AcyclicPredicate: " + acyclicPredicate.test(relation));

        final SolvingStrategy solvingStrategy = new SolvingStrategy(new PropertiesDerivationStrategy());
        final RelationPropertyViolationFindingStrategy relationPropertyViolationFindingStrategy =
                new RelationPropertyViolationFindingStrategy();

        new ReadInputController().readInput()
                .forEach(relation -> {

                    RelationClass relationClass = solvingStrategy.solve(relation);
                    relationPropertyViolationFindingStrategy.findViolations(relation);

                    relation.print();
                    System.out.println("Relation class: " + relationClass);
                });
    }
}
