package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.criteria.EquivalentIncomparabilityRelationCriteria;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class RelationSimilarClassesChoosingStrategy {

    private final Map<Set<RelationClass>, Function<Relation, RelationClass>> rules =
            Map.ofEntries(
                    Map.entry(Set.of(RelationClass.STRICT_ORDER, RelationClass.WEAK_ORDERING), new EquivalentIncomparabilityRelationCriteria())
            );

    public void cleanupSimilarClasses(final Relation relation) {

        rules.entrySet().stream()
                .filter(entry -> relation.getClasses().containsAll(entry.getKey()))
                .peek(entry -> relation.getClasses().removeAll(entry.getKey()))
                .forEach(entry -> relation.getClasses().add(entry.getValue().apply(relation)));
    }
}
