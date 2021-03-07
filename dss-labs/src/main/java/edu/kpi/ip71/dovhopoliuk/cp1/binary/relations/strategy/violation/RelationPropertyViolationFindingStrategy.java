package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;

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
