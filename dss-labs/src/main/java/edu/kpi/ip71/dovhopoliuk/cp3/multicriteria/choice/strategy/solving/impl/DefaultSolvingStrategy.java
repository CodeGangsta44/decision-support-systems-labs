package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.solving.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.RelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl.BerezovskyRelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl.LexicographyRelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl.MajorityRelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl.ParetoRelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl.PodinovskyRelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.solving.SolvingStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultSolvingStrategy implements SolvingStrategy {

    private static final List<RelationDerivationStrategy> derivationStrategies =
            List.of(
                    new ParetoRelationDerivationStrategy(),
                    new MajorityRelationDerivationStrategy(),
                    new LexicographyRelationDerivationStrategy(),
                    new BerezovskyRelationDerivationStrategy(),
                    new PodinovskyRelationDerivationStrategy()
            );

    @Override
    public List<Relation> solve(final CriteriaInfo criteriaInfo) {

        return derivationStrategies.stream()
                .map(strategy -> strategy.derivate(criteriaInfo))
                .collect(Collectors.toList());
    }
}
