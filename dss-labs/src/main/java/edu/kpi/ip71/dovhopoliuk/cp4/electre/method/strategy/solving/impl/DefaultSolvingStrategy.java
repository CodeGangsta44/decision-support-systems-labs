package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.solving.impl;

import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.RelationOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp2.optimization.methods.strategy.optimization.impl.nm.RelationNeumannMorgensternOptimizationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation.RelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.derivation.impl.ElectreRelationDerivationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.population.ElectrePopulationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.population.impl.ElectreConcordanceDiscordancePopulationStrategy;
import edu.kpi.ip71.dovhopoliuk.cp4.electre.method.strategy.solving.SolvingStrategy;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.SPACE;

public class DefaultSolvingStrategy implements SolvingStrategy {

    private static final String CONCORDANCE_MATRIX_LABEL = "Матриця індексів узгодження C\n";
    private static final String DISCORDANCE_MATRIX_LABEL = "Матриця індексів неузгодження D\n";
    private static final String CONCORDANCE_DISCORDANCE_INDEXES_LABEL = "Значення порогів для індексів узгодження та неузгодження c, d\n";
    private static final String RELATION_MATRIX_LABEL = "Відношення для порогових значень c, d:\n";
    private static final String CORE_LABEL = "Ядро відношення:\n";
    private static final String FORMATTER_PATTERN = "0.000";

    private final ElectrePopulationStrategy electrePopulationStrategy;
    private final RelationDerivationStrategy relationDerivationStrategy;
    private final RelationOptimizationStrategy nmRelationOptimizationStrategy;
    private final DecimalFormat formatter;

    public DefaultSolvingStrategy() {

        this.electrePopulationStrategy = new ElectreConcordanceDiscordancePopulationStrategy();
        this.relationDerivationStrategy = new ElectreRelationDerivationStrategy();
        this.nmRelationOptimizationStrategy = new RelationNeumannMorgensternOptimizationStrategy();

        this.formatter = new DecimalFormat(FORMATTER_PATTERN);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
    }

    @Override
    public String solve(final ElectreInfo electreInfo) {

        electrePopulationStrategy.populate(electreInfo);
        final Relation relation = relationDerivationStrategy.derivate(electreInfo);

        final Set<Integer> core = nmRelationOptimizationStrategy.getOptimizedResultAsSet(relation);

        return buildResultString(electreInfo, relation, core);
    }

    private String buildResultString(final ElectreInfo electreInfo, final Relation relation, final Set<Integer> core) {

        return getConcordanceMatrix(electreInfo)
                + getDiscordanceMatrix(electreInfo)
                + getConcordanceAndDiscordanceIndexes(electreInfo)
                + getRelationMatrix(relation)
                + getCore(core, relation);
    }

    private String getConcordanceMatrix(final ElectreInfo electreInfo) {

        return CONCORDANCE_MATRIX_LABEL + electreInfo.getConcordanceMatrix().stream()
                .map(row -> row.stream()
                        .map(formatter::format)
                        .collect(Collectors.joining(SPACE)))
                .collect(Collectors.joining("\n")) + "\n";
    }

    private String getDiscordanceMatrix(final ElectreInfo electreInfo) {

        return DISCORDANCE_MATRIX_LABEL + electreInfo.getDiscordanceMatrix().stream()
                .map(row -> row.stream()
                        .map(formatter::format)
                        .collect(Collectors.joining(SPACE)))
                .collect(Collectors.joining("\n")) + "\n";
    }

    private String getConcordanceAndDiscordanceIndexes(final ElectreInfo electreInfo) {

        return CONCORDANCE_DISCORDANCE_INDEXES_LABEL
                + formatter.format(electreInfo.getC())
                + SPACE
                + formatter.format(electreInfo.getD())
                + "\n";
    }

    private String getRelationMatrix(final Relation relation) {

        return RELATION_MATRIX_LABEL + relation.getMatrix().stream()
                .map(row -> row.stream()
                        .map(value -> value ? INTEGER_ONE : INTEGER_ZERO)
                        .map(Object::toString)
                        .collect(Collectors.joining(SPACE)))
                .collect(Collectors.joining("\n")) + "\n";
    }

    private String getCore(final Set<Integer> core, final Relation relation) {

        return CORE_LABEL + core.stream()
                .sorted()
                .map(relation::getElementName)
                .collect(Collectors.joining(SPACE));
    }
}
