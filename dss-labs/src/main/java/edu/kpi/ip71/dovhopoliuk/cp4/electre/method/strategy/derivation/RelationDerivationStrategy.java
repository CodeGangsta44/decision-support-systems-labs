package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

public interface RelationDerivationStrategy {

    Relation derivate(final ElectreInfo electreInfo);
}
