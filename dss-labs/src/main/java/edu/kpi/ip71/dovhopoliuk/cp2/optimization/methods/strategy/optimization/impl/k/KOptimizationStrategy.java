package edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.k;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationType;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.AbstractRelationOptimizationStrategy;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class KOptimizationStrategy extends AbstractRelationOptimizationStrategy {

    private static final Set<RelationProperty> requiredProperties = Collections.emptySet();
    private static final Set<RelationProperty> restrictedProperties = Set.of(RelationProperty.ACYCLITY);

    private static final Map<BiPredicate<Boolean, Boolean>, RelationType> relationTypePredicates =
            Map.ofEntries(
                    Map.entry((first, second) -> first && !second, RelationType.P),
                    Map.entry((first, second) -> first && second, RelationType.I),
                    Map.entry((first, second) -> !first && !second, RelationType.N),
                    Map.entry((first, second) -> !first && second, RelationType.ZERO)
            );

    private static final Map<Integer, Set<RelationType>> mapOfUsedTypesForK =
            Map.ofEntries(
                    Map.entry(1, Set.of(RelationType.P, RelationType.I, RelationType.N)),
                    Map.entry(2, Set.of(RelationType.P, RelationType.N)),
                    Map.entry(3, Set.of(RelationType.P, RelationType.I)),
                    Map.entry(4, Set.of(RelationType.P))
            );

    public KOptimizationStrategy() {

        super(requiredProperties, restrictedProperties);
    }

    @Override
    public String getOptimizedResult(final Relation relation) {

        final var relationMatrix = getRelationMatrixWithTypes(relation);

        return mapOfUsedTypesForK.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(entry -> "K = " + entry.getKey() + "\n" + solveForK(relationMatrix, entry.getValue(), relation, entry.getKey()))
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public Set<Integer> getOptimizedResultAsSet(final Relation relation) {

        throw new UnsupportedOperationException();
    }

    private List<List<RelationType>> getRelationMatrixWithTypes(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation.getSize())
                        .mapToObj(columnIndex -> getRelationType(rowIndex, columnIndex, relation))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private RelationType getRelationType(final int rowIndex, final int columnIndex, final Relation relation) {

        return relationTypePredicates.entrySet().stream()
                .filter(entry -> entry.getKey().test(relation.getElement(rowIndex, columnIndex), relation.getElement(columnIndex, rowIndex)))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(RelationType.ZERO);
    }

    private String solveForK(final List<List<RelationType>> matrix, final Set<RelationType> usedTypes, final Relation relation, final int k) {

        final List<List<Integer>> subsetsByK = matrix.stream()
                .map(row -> IntStream.range(INTEGER_ZERO, matrix.size())
                        .filter(columnIndex -> usedTypes.contains(row.get(columnIndex)))
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return k + "-max = " + concatenateValues(getMaxForK(subsetsByK), relation) + "\n"
                + k + "-opt = " + concatenateValues(getOptimalForK(subsetsByK, relation), relation);
    }

    private List<Integer> getMaxForK(final List<List<Integer>> subsetsByK) {

        return IntStream.range(INTEGER_ZERO, subsetsByK.size())
                .filter(index -> subsetsByK.stream().allMatch(subset -> subsetsByK.get(index).containsAll(subset)))
                .boxed()
                .collect(Collectors.toList());
    }

    private List<Integer> getOptimalForK(final List<List<Integer>> subsetsByK, final Relation relation) {

        return IntStream.range(INTEGER_ZERO, subsetsByK.size())
                .filter(index -> subsetsByK.get(index).containsAll(relation.getRelationSet()))
                .boxed()
                .collect(Collectors.toList());
    }

    private String concatenateValues(final Collection<Integer> values, final Relation relation) {

        return "{" + values.stream().map(relation::getElementName).collect(Collectors.joining(", ")) + "}";
    }
}
