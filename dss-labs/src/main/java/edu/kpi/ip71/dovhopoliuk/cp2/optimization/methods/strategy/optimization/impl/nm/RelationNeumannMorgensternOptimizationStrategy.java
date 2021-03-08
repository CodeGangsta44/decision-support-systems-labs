package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.nm;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.util.RelationalOperationsUtil;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.AbstractRelationOptimizationStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class RelationNeumannMorgensternOptimizationStrategy extends AbstractRelationOptimizationStrategy {

    private static final Set<RelationProperty> requiredProperties = Set.of(RelationProperty.ACYCLITY);
    private static final Set<RelationProperty> restrictedProperties = Collections.emptySet();

    public RelationNeumannMorgensternOptimizationStrategy() {

        super(requiredProperties, restrictedProperties);
    }

    @Override
    public String getOptimizedResult(final Relation relation) {

        return buildResultString(getResult(getSCollectionIncrements(getInitialSCollection(relation), relation), relation), relation);
    }

    private Set<Integer> getInitialSCollection(final Relation relation) {

        return relation.getRelationSet().stream()
                .filter(element -> relation.getUpperSection(element).isEmpty())
                .collect(Collectors.toSet());
    }

    private List<Set<Integer>> getSCollectionIncrements(final Set<Integer> initialCollection, final Relation relation) {

        final List<Set<Integer>> collectionIncrements = new ArrayList<>();
        final Set<Integer> sCollection = new HashSet<>(initialCollection);

        collectionIncrements.add(initialCollection);

        while (!doesCollectionIncrementsContainAllSet(collectionIncrements, relation)) {

            final Set<Integer> incrementToAdd = getRemainingElements(sCollection, relation).stream()
                    .filter(element -> sCollection.containsAll(relation.getUpperSection(element)))
                    .collect(Collectors.toSet());

            collectionIncrements.add(incrementToAdd);
            sCollection.addAll(incrementToAdd);
        }

        return collectionIncrements;
    }

    private boolean doesCollectionIncrementsContainAllSet(final List<Set<Integer>> collectionIncrements, final Relation relation) {

        return collectionIncrements.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .containsAll(relation.getRelationSet());
    }

    private Set<Integer> getRemainingElements(Set<Integer> usedElements, final Relation relation) {

        return relation.getRelationSet().stream()
                .filter(Predicate.not(usedElements::contains))
                .collect(Collectors.toSet());
    }

    private Set<Integer> getFirstIncrement(final List<Set<Integer>> collectionIncrements) {

        return collectionIncrements.get(INTEGER_ZERO);
    }

    private Set<Integer> getResult(final List<Set<Integer>> sCollectionIncrement, final Relation relation) {

        final Set<Integer> result = new LinkedHashSet<>(getFirstIncrement(sCollectionIncrement));

        sCollectionIncrement.stream()
                .skip(INTEGER_ONE)
                .forEach(increment -> result.addAll(getSuitableElementsFromIncrement(increment, result, relation)));

        return result;
    }

    private Set<Integer> getSuitableElementsFromIncrement(final Set<Integer> increment, final Set<Integer> result, final Relation relation) {

        return increment.stream()
                .filter(element -> RelationalOperationsUtil.intersectCollections(relation.getUpperSection(element), result).isEmpty())
                .collect(Collectors.toSet());
    }

    private boolean performCheckOfResult(final Set<Integer> result, final Relation relation) {

        return checkInternalStabilityOfResult(result, relation) && checkExternalStabilityOfResult(result, relation);
    }

    private boolean checkInternalStabilityOfResult(final Set<Integer> result, final Relation relation) {

        return result.stream()
                .allMatch(element -> result.stream()
                        .filter(innerElement -> !innerElement.equals(element))
                        .allMatch(innerElement -> checkInternalStabilityCondition(element, innerElement, relation)));
    }

    private boolean checkInternalStabilityCondition(final int firstElement, final int secondElement, final Relation relation) {

        return !relation.getElement(firstElement, secondElement) && !relation.getElement(secondElement, firstElement);
    }

    private boolean checkExternalStabilityOfResult(final Set<Integer> result, final Relation relation) {

        return relation.getRelationSet().stream()
                .filter(Predicate.not(result::contains))
                .allMatch(element -> result.stream()
                        .anyMatch(elementFromResult -> relation.getElement(elementFromResult, element)));
    }

    private String buildResultString(final Set<Integer> result, final Relation relation) {

        return "X = {" + result.stream().map(relation::getElementName).collect(Collectors.joining(", ")) + "}\n"
                + "Check of internal and external stability passed: " + performCheckOfResult(result, relation);
    }
}
