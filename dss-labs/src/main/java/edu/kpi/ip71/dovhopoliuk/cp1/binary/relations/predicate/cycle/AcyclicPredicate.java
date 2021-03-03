package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.cycle;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.predicate.reflexivity.AntiReflexivePredicate;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.constants.Constants.INTEGER_ZERO;

public class AcyclicPredicate implements Predicate<Relation> {

    private final Predicate<Relation> antiReflexivePredicate = new AntiReflexivePredicate();

    @Override
    public boolean test(final Relation relation) {

        return antiReflexivePredicate.test(relation) &&
                IntStream.range(INTEGER_ZERO, relation.getSize())
                        .noneMatch(index -> doesCycleExistForRoot(relation, index));
    }

    private boolean doesCycleExistForRoot(final Relation relation, final int rootIndex) {

        Stack<Integer> solutionStack = new Stack<>();
        Stack<Stack<Integer>> variantsStack = new Stack<>();
        solutionStack.push(rootIndex);
        variantsStack.push(createStackFromStream(relation.getLowerSection(rootIndex).stream()));

        while (!isStackCycled(solutionStack, rootIndex) && !solutionStack.empty()) {

            getNextElement(variantsStack)
                    .ifPresentOrElse(index -> addNextVariants(relation, variantsStack, solutionStack, index, rootIndex),
                            solutionStack::pop);

        }

        return isStackCycled(solutionStack, rootIndex);
    }

    private Optional<Integer> getNextElement(final Stack<Stack<Integer>> variantsStack) {

        return Optional.of(variantsStack.pop())
                .filter(Predicate.not(Collection::isEmpty))
                .map(stack -> getNextElementFromStack(variantsStack, stack));
    }

    private int getNextElementFromStack(final Stack<Stack<Integer>> variantsStack, final Stack<Integer> currentVariantSet) {

        final Integer result = currentVariantSet.pop();
        variantsStack.push(createStackFromStream(currentVariantSet.stream()));
        return result;
    }

    private void addNextVariants(final Relation relation, final Stack<Stack<Integer>> variantsStack, final Stack<Integer> solutionStack,
                                 final int currentIndex, final int rootIndex) {

        solutionStack.push(currentIndex);
        variantsStack.push(createStackFromStream(relation.getLowerSection(currentIndex).stream()
                .filter(index -> !solutionStack.contains(index) || index == rootIndex)));

    }

    private boolean isStackCycled(final Stack<Integer> solutionStack, final int rootIndex) {

        return solutionStack.lastIndexOf(rootIndex) > INTEGER_ZERO;
    }

    private Stack<Integer> createStackFromStream(final Stream<Integer> stream) {

        final Stack<Integer> result = new Stack<>();
        stream.forEach(result::push);
        return result;
    }
}