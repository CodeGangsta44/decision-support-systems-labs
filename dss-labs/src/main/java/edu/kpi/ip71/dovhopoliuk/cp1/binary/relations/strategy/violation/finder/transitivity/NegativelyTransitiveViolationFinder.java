package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy.violation.finder.transitivity;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationPropertyViolation;
import edu.kpi.ip71.dovhopoliuk.common.util.RelationalOperationsUtil;

import java.util.List;
import java.util.function.BiFunction;

public class NegativelyTransitiveViolationFinder implements BiFunction<Relation, RelationProperty, RelationPropertyViolation> {

    private final BiFunction<Relation, RelationProperty, RelationPropertyViolation> transitiveViolationFinder = new TransitiveViolationFinder();

    @Override
    public RelationPropertyViolation apply(final Relation relation, final RelationProperty property) {

        return RelationPropertyViolation.builder()
                .property(property)
                .messages(findViolations(relation, property))
                .build();
    }

    private List<String> findViolations(final Relation relation, final RelationProperty property) {

        return transitiveViolationFinder.apply(RelationalOperationsUtil.invert(relation), property)
                .getMessages();
    }
}
