package edu.kpi.ip71.dovhopoliuk.cp3.multicriteria.choice.strategy.derivation.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.util.RelationalOperationsUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_MINUS_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class BerezovskyRelationDerivationStrategy extends AbstractRelationDerivationStrategy {

    private static final String STRATEGY_NAME = "Berezovsky";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class ParetoSystem {

        private List<List<Boolean>> pMatrix;
        private List<List<Boolean>> iMatrix;
        private List<List<Boolean>> nMatrix;
    }

    private static class BerezovskySystem extends ParetoSystem {
    }

    @FunctionalInterface
    public interface QuadroPredicate<T, U, R, V> {

        boolean test(T t, U u, R r, V v);
    }

    public BerezovskyRelationDerivationStrategy() {

        super(STRATEGY_NAME);
    }

    @Override
    protected List<List<Boolean>> createRelationMatrix(final CriteriaInfo criteriaInfo) {

        final List<ParetoSystem> paretoSystems = criteriaInfo.getInvertedQuasiOrderOfImportance().stream()
                .map(criteria -> buildParetoSystem(criteria, criteriaInfo))
                .collect(Collectors.toList());

        final BerezovskySystem berezovskySystem = buildBerezovskySystem(paretoSystems);

        return berezovskySystem.getPMatrix();
    }

    private ParetoSystem buildParetoSystem(final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return ParetoSystem.builder()
                .pMatrix(buildParetoPMatrix(criteria, criteriaInfo))
                .iMatrix(buildParetoIMatrix(criteria, criteriaInfo))
                .nMatrix(buildParetoNMatrix(criteria, criteriaInfo))
                .build();
    }

    private List<List<Boolean>> buildParetoMatrix(final Set<Integer> criteria, final CriteriaInfo criteriaInfo,
                                                  QuadroPredicate<Integer, Integer, Set<Integer>, CriteriaInfo> conditionPredicate) {

        return IntStream.range(INTEGER_ZERO, criteriaInfo.getMarks().size())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, criteriaInfo.getMarks().size())
                        .mapToObj(columnIndex -> conditionPredicate.test(rowIndex, columnIndex, criteria, criteriaInfo))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<Boolean>> buildParetoPMatrix(final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return buildParetoMatrix(criteria, criteriaInfo, this::checkParetoPCondition);
    }

    private boolean checkParetoPCondition(final int rowIndex, final int columnIndex, final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return Optional.of(buildParetoList(rowIndex, columnIndex, criteria, criteriaInfo))
                .filter(list -> !list.contains(INTEGER_MINUS_ONE))
                .filter(list -> list.contains(INTEGER_ONE))
                .isPresent();
    }

    private List<List<Boolean>> buildParetoIMatrix(final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return buildParetoMatrix(criteria, criteriaInfo, this::checkParetoICondition);
    }

    private boolean checkParetoICondition(final int rowIndex, final int columnIndex, final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return Optional.of(buildParetoList(rowIndex, columnIndex, criteria, criteriaInfo))
                .filter(list -> !list.contains(INTEGER_MINUS_ONE))
                .filter(list -> !list.contains(INTEGER_ONE))
                .isPresent();
    }

    private List<List<Boolean>> buildParetoNMatrix(final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return buildParetoMatrix(criteria, criteriaInfo, this::checkParetoNCondition);
    }

    private boolean checkParetoNCondition(final int rowIndex, final int columnIndex, final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return Optional.of(buildParetoList(rowIndex, columnIndex, criteria, criteriaInfo))
                .filter(list -> list.contains(INTEGER_MINUS_ONE))
                .filter(list -> list.contains(INTEGER_ONE))
                .isPresent();
    }

    private List<Integer> buildParetoList(final int rowIndex, final int columnIndex, final Set<Integer> criteria, final CriteriaInfo criteriaInfo) {

        return criteria.stream()
                .map(criteriaIndex -> RelationalOperationsUtil.sign(criteriaInfo.getMark(rowIndex, criteriaIndex) - criteriaInfo.getMark(columnIndex, criteriaIndex)))
                .collect(Collectors.toList());
    }

    private BerezovskySystem buildBerezovskySystem(final List<ParetoSystem> paretoSystems) {

        final BerezovskySystem result = buildInitialBerezovskySystem(paretoSystems.get(INTEGER_ZERO));

        paretoSystems.stream()
                .skip(INTEGER_ONE)
                .forEach(paretoSystem -> modifyBerezovskySystem(result, paretoSystem));

        return result;
    }

    private BerezovskySystem buildInitialBerezovskySystem(final ParetoSystem firstParetoSystem) {

        final BerezovskySystem result = new BerezovskySystem();

        result.setPMatrix(firstParetoSystem.getPMatrix());
        result.setIMatrix(firstParetoSystem.getIMatrix());
        result.setNMatrix(firstParetoSystem.getNMatrix());

        return result;
    }

    private void modifyBerezovskySystem(final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        List<List<Boolean>> berezovskyPMatrix = getBerezovskyPMatrix(berezovskySystem, paretoSystem);
        List<List<Boolean>> berezovskyIMatrix = getBerezovskyIMatrix(berezovskySystem, paretoSystem);

        berezovskySystem.setPMatrix(berezovskyPMatrix);
        berezovskySystem.setIMatrix(berezovskyIMatrix);

        List<List<Boolean>> berezovskyNMatrix = getBerezovskyNMatrix(berezovskySystem, paretoSystem);

        berezovskySystem.setNMatrix(berezovskyNMatrix);
    }

    private List<List<Boolean>> getBerezovskyMatrix(final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem,
                                                    QuadroPredicate<Integer, Integer, BerezovskySystem, ParetoSystem> conditionPredicate) {

        return IntStream.range(INTEGER_ZERO, paretoSystem.getPMatrix().size())
                .mapToObj(rowIndex -> IntStream.range(INTEGER_ZERO, paretoSystem.getPMatrix().size())
                        .mapToObj(columnIndex -> conditionPredicate.test(rowIndex, columnIndex, berezovskySystem, paretoSystem))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<Boolean>> getBerezovskyPMatrix(final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return getBerezovskyMatrix(berezovskySystem, paretoSystem, this::checkBerezovskyPCondition);
    }

    private List<List<Boolean>> getBerezovskyIMatrix(final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return getBerezovskyMatrix(berezovskySystem, paretoSystem, this::checkBerezovskyICondition);
    }

    private List<List<Boolean>> getBerezovskyNMatrix(final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return getBerezovskyMatrix(berezovskySystem, paretoSystem, this::checkBerezovskyNCondition);
    }

    private boolean checkBerezovskyPCondition(final int rowIndex, final int columnIndex, final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return (paretoSystem.getPMatrix().get(rowIndex).get(columnIndex) && !berezovskySystem.getPMatrix().get(columnIndex).get(rowIndex))
                ||
                (paretoSystem.getIMatrix().get(rowIndex).get(columnIndex) && berezovskySystem.getPMatrix().get(rowIndex).get(columnIndex));
    }

    private boolean checkBerezovskyICondition(final int rowIndex, final int columnIndex, final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return paretoSystem.getIMatrix().get(rowIndex).get(columnIndex) && berezovskySystem.getIMatrix().get(rowIndex).get(columnIndex);
    }

    private boolean checkBerezovskyNCondition(final int rowIndex, final int columnIndex, final BerezovskySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return !(berezovskySystem.getPMatrix().get(rowIndex).get(columnIndex)
                || berezovskySystem.getPMatrix().get(columnIndex).get(rowIndex)
                || berezovskySystem.getIMatrix().get(rowIndex).get(columnIndex));
    }
}
