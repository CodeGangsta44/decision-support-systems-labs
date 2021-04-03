package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.violation;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FuzzyRelationPropertyViolationFindingStrategy {

    public void findViolations(final FuzzyRelation relation) {

        relation.setViolations(Arrays.stream(FuzzyRelationProperty.values())
                .filter(property -> !relation.getProperties().contains(property))
                .map(property -> property.findViolations(relation))
                .collect(Collectors.toList()));
    }
}
