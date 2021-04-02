package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation.RelationDerivationStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ElectreRelationDerivationStrategy implements RelationDerivationStrategy {

    private static final String STRATEGY_NAME = "ELECTRE";

    @Override
    public Relation derivate(final ElectreInfo electreInfo) {

        return Relation.builder()
                .relationName(STRATEGY_NAME)
                .matrix(createRelationMatrix(electreInfo))
                .elements(generateElementNames(electreInfo.getSize()))
                .properties(new HashSet<>())
                .classes(new HashSet<>())
                .build();
    }

    protected List<List<Boolean>> createRelationMatrix(final ElectreInfo electreInfo) {

        return IntStream.range(INTEGER_ZERO, electreInfo.getSize())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, electreInfo.getSize())
                        .mapToObj(columnIndex -> electreInfo.getConcordanceIndex(rowIndex, columnIndex) >= electreInfo.getC()
                                && electreInfo.getDiscordanceIndex(rowIndex, columnIndex) <= electreInfo.getD())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<String> generateElementNames(final int size) {

        return IntStream.range(INTEGER_ONE, size + INTEGER_ONE)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }
}
