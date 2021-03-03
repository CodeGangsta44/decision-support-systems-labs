package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy;

import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity.RelationProperty;

import java.util.Arrays;
import java.util.Comparator;

public class SolvingStrategy {

    private final PropertiesDerivationStrategy propertiesDerivationStrategy;
    private final RelationSimilarClassesChoosingStrategy relationSimilarClassesChoosingStrategy;

    public SolvingStrategy(final PropertiesDerivationStrategy propertiesDerivationStrategy,
                           final RelationSimilarClassesChoosingStrategy relationSimilarClassesChoosingStrategy) {

        this.propertiesDerivationStrategy = propertiesDerivationStrategy;
        this.relationSimilarClassesChoosingStrategy = relationSimilarClassesChoosingStrategy;
    }

    public RelationClass solve(final Relation relation) {

        analyzeProperties(relation);

        derivateProperties(relation);

        foundClasses(relation);

        cleanUpSimilarClasses(relation);

        return determineMainClass(relation);
    }

    private void analyzeProperties(final Relation relation) {

        Arrays.stream(RelationProperty.values())
                .filter(property -> property.test(relation))
                .forEach(property -> relation.getProperties().add(property));
    }

    private void derivateProperties(final Relation relation) {

        propertiesDerivationStrategy.derivate(relation);
    }

    private void foundClasses(final Relation relation) {

        Arrays.stream(RelationClass.values())
                .filter(relationClass -> relationClass.test(relation))
                .forEach(relationClass -> relation.getClasses().add(relationClass));
    }

    private void cleanUpSimilarClasses(final Relation relation) {

       relationSimilarClassesChoosingStrategy.cleanupSimilarClasses(relation);
    }

    private RelationClass determineMainClass(final Relation relation) {

        return relation.getClasses().stream()
                .min(Comparator.comparingInt(Enum::ordinal))
                .orElse(RelationClass.UNDEFINED);
    }
}
