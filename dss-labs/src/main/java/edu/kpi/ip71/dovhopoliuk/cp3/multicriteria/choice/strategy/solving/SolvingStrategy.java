package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.solving;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

import java.util.List;

public interface SolvingStrategy {

    List<Relation> solve(final CriteriaInfo criteriaInfo);
}
