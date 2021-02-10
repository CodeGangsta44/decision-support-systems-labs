package edu.kpi.ip71.dovhopoliuk.predicate.cycle;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.predicate.reflexivity.AntiReflexivePredicate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.INTEGER_ZERO;

public class AcyclicPredicate implements Predicate<Relation> {

    private final Predicate<Relation> antiReflexivePredicate = new AntiReflexivePredicate();

    @Override
    public boolean test(final Relation relation) {

        return antiReflexivePredicate.test(relation) &&
                IntStream.range(INTEGER_ZERO, relation.getSize())
                        .noneMatch(index -> doesCycleExistForRoot(relation, index));
    }

    private boolean doesCycleExistForRoot(final Relation relation, final int rootIndex) {

        Set<Integer> indexesToSolve = new HashSet<>();
        Optional<Integer> currentIndex = Optional.of(rootIndex);

        while (currentIndex.isPresent() && !indexesToSolve.contains(rootIndex)) {

            indexesToSolve.remove(currentIndex.get());
            indexesToSolve.addAll(relation.getLowerSection(currentIndex.get()));
            currentIndex = indexesToSolve.stream().findFirst();
        }

        return indexesToSolve.contains(rootIndex);
    }
}