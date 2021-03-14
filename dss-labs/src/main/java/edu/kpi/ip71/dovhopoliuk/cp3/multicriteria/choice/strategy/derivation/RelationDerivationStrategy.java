package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

public interface RelationDerivationStrategy {

    Relation derivate(final CriteriaInfo criteriaInfo);
}
