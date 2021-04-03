package edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.strategy.solving;

import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;
import edu.kpi.ip71.dovhopoliuk.cp5.utils.PrintUtils;
import edu.kpi.ip71.dovhopoliuk.cp6.fuzzy.relation.utils.FuzzyRelationOperationUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.DOUBLE_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class ExpertSolvingStrategy {

    public void solveExpertTask(final FuzzyRelation relation) {

        System.out.println("\n\n-=== STARTING EXPERT TASK ===-");

        relation.print();
        final FuzzyRelation strictAdvantageRelation = FuzzyRelationOperationUtils.buildStrictAdvantage(relation);
        strictAdvantageRelation.print();

        final List<Map.Entry<Integer, Double>> dominationList = createDominationList(strictAdvantageRelation);
        PrintUtils.printTitleAndListOfEntries("Dominated: ", dominationList);

        final List<Map.Entry<Integer, Double>> nonDominationList = createNonDominationList(dominationList);
        PrintUtils.printTitleAndListOfEntries("Non dominated: ", nonDominationList);

        final List<Map.Entry<Integer, Double>> rankedList = performRanking(nonDominationList);
        PrintUtils.printTitleAndListOfEntries("Ranked: ", rankedList);

        System.out.println("Best alternative: " + relation.getElementName(rankedList.get(INTEGER_ZERO).getKey()));
    }

    private List<Map.Entry<Integer, Double>> createDominationList(final FuzzyRelation strictAdvantageRelation) {

        return IntStream.range(INTEGER_ZERO, strictAdvantageRelation.getSize())
                .mapToObj(columnIndex -> Map.entry(columnIndex, IntStream.range(INTEGER_ZERO, strictAdvantageRelation.getSize())
                        .mapToDouble(rowIndex -> strictAdvantageRelation.getElement(rowIndex, columnIndex)).max().orElse(DOUBLE_ONE)))
                .collect(Collectors.toList());
    }

    private List<Map.Entry<Integer, Double>> createNonDominationList(final List<Map.Entry<Integer, Double>> dominationList) {

        return dominationList.stream()
                .map(entry -> Map.entry(entry.getKey(), DOUBLE_ONE - entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<Map.Entry<Integer, Double>> performRanking(final List<Map.Entry<Integer, Double>> list) {

        return list.stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());
    }
}
