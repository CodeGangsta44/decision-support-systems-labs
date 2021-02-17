package edu.kpi.ip71.dovhopoliuk.strategy.violation;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.entity.RelationProperty;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RelationPropertyViolationFindingStrategy {

    public void findViolations(final Relation relation) {

        relation.setViolations(Arrays.stream(RelationProperty.values())
                .filter(property -> !relation.getProperties().contains(property))
                .map(property -> property.findViolations(relation))
                .collect(Collectors.toList()));
    }
}
