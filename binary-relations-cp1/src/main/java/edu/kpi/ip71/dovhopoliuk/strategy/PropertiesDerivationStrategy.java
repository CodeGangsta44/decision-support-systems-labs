package edu.kpi.ip71.dovhopoliuk.strategy;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.entity.RelationProperty;

import java.util.Map;
import java.util.Set;

public class PropertiesDerivationStrategy {

    private final Map<Set<RelationProperty>, Set<RelationProperty>> derivationRules =
            Map.ofEntries(
                    Map.entry(Set.of(RelationProperty.ANTIREFLEXIVITY, RelationProperty.TRANSITIVITY), Set.of(RelationProperty.ASYMMETRY, RelationProperty.ACYCLITY)),
                    Map.entry(Set.of(RelationProperty.ASYMMETRY, RelationProperty.NEGATIVE_TRANSITIVITY), Set.of(RelationProperty.TRANSITIVITY)),
                    Map.entry(Set.of(RelationProperty.TRANSITIVITY, RelationProperty.CONNECTIVITY), Set.of(RelationProperty.REFLEXIVITY, RelationProperty.NEGATIVE_TRANSITIVITY)),
                    Map.entry(Set.of(RelationProperty.CONNECTIVITY), Set.of(RelationProperty.REFLEXIVITY))
            );

    public void derivate(final Relation relation) {

        derivationRules.entrySet()
                .stream()
                .filter(entry -> relation.getProperties().containsAll(entry.getKey()))
                .forEach(entry -> relation.getProperties().addAll(entry.getValue()));
    }
}
