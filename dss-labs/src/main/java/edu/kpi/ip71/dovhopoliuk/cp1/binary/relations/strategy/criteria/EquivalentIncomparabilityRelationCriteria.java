package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.criteria;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.constants.Constants.INTEGER_ZERO;

public class EquivalentIncomparabilityRelationCriteria implements Function<Relation, RelationClass> {

    private final RelationProperty reflexivityProperty = RelationProperty.REFLEXIVITY;
    private final RelationProperty symmetryProperty = RelationProperty.SYMMETRY;
    private final RelationProperty transitivityProperty = RelationProperty.TRANSITIVITY;


    @Override
    public RelationClass apply(final Relation relation) {

        return Optional.of(test(relation))
                .filter(Boolean.TRUE::equals)
                .map(answer -> RelationClass.WEAK_ORDERING)
                .orElse(RelationClass.STRICT_ORDER);
    }

    public boolean test(final Relation relation) {

        final Relation incomparabilityRelation = getIncomparabilityRelation(relation);

        return reflexivityProperty.test(incomparabilityRelation)
                && symmetryProperty.test(incomparabilityRelation)
                && transitivityProperty.test(incomparabilityRelation);
    }

    private Relation getIncomparabilityRelation(final Relation relation) {

        return Relation.builder()
                .elements(relation.getElements())
                .properties(new HashSet<>())
                .matrix(getIncomparabilityMatrix(relation))
                .build();
    }

    private List<List<Boolean>> getIncomparabilityMatrix(final Relation relation) {

        return IntStream.range(INTEGER_ZERO, relation.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, relation.getSize())
                        .mapToObj(columnIndex -> Boolean.FALSE.equals(relation.getElement(rowIndex, columnIndex))
                                && Boolean.FALSE.equals(relation.getElement(columnIndex, rowIndex)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
