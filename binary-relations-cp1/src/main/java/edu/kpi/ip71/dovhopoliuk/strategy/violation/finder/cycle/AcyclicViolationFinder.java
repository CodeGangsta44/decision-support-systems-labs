package edu.kpi.ip71.dovhopoliuk.strategy.violation.finder.cycle;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.entity.RelationPropertyViolation;
import edu.kpi.ip71.dovhopoliuk.predicate.reflexivity.AntiReflexivePredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.INTEGER_ZERO;

public class AcyclicViolationFinder implements BiFunction<Relation, RelationProperty, RelationPropertyViolation> {

    private final Predicate<Relation> antiReflexivePredicate = new AntiReflexivePredicate();

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation))
                .build();
    }

    private List<String> findViolations(final Relation relation) {

        return Stream.concat(findCycleViolations(relation),
                checkAntiReflexiveViolation(relation).stream())
                .collect(Collectors.toList());
    }

    public Stream<String> findCycleViolations(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .boxed()
                .map(index -> getCycleForRoot(relation, index))
                .filter(Predicate.not(Collection::isEmpty))
                .map(cycle -> buildCycleViolationMessage(cycle, relation));
    }

    private Optional<String> checkAntiReflexiveViolation(final Relation relation) {

        return Optional.of(antiReflexivePredicate.test(relation))
                .filter(Boolean.FALSE::equals)
                .map(result -> buildPropertyViolationMessage(RelationProperty.ANTIREFLEXIVITY.toString()));
    }

    private List<Integer> getCycleForRoot(final Relation relation, final int rootIndex) {

        Stack<Integer> solutionStack = new Stack<>();
        Stack<Stack<Integer>> variantsStack = new Stack<>();
        solutionStack.push(rootIndex);
        variantsStack.push(createStackFromStream(relation.getLowerSection(rootIndex).stream()));

        while (!isStackCycled(solutionStack, rootIndex) && !solutionStack.empty()) {

            getNextElement(variantsStack)
                    .ifPresentOrElse(index -> addNextVariants(relation, variantsStack, solutionStack, index, rootIndex),
                            solutionStack::pop);

        }

        return new ArrayList<>(solutionStack);
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

    private String buildCycleViolationMessage(final List<Integer> cycle, final Relation relation) {

        return "On relation exists cycle: " + cycle.stream()
                .map(relation::getElementName)
                .collect(Collectors.joining("->"));
    }

    private String buildPropertyViolationMessage(final String propertyName) {

        return "Relation does not have " + propertyName + " property";
    }
}
