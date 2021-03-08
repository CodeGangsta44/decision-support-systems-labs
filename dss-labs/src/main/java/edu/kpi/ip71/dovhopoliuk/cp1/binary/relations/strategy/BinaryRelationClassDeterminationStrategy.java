package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.strategy;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.common.entity.RelationProperty;

import java.util.Arrays;
import java.util.Comparator;

public class BinaryRelationClassDeterminationStrategy {

    private final PropertiesDerivationStrategy propertiesDerivationStrategy;
    private final RelationSimilarClassesChoosingStrategy relationSimilarClassesChoosingStrategy;

    public BinaryRelationClassDeterminationStrategy(final PropertiesDerivationStrategy propertiesDerivationStrategy,
                                                    final RelationSimilarClassesChoosingStrategy relationSimilarClassesChoosingStrategy) {

        this.propertiesDerivationStrategy = propertiesDerivationStrategy;
        this.relationSimilarClassesChoosingStrategy = relationSimilarClassesChoosingStrategy;
    }

    public RelationClass solve(final Relation relation) {

        analyzeProperties(relation);

        derivateProperties(relation);

        foundClasses(relation);

//        cleanUpSimilarClasses(relation);

        determineMainClass(relation);

        return relation.getMainClass();
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

    private void determineMainClass(final Relation relation) {

        relation.getClasses().stream()
                .min(Comparator.comparingInt(Enum::ordinal))
                .ifPresentOrElse(relation::setMainClass,
                        () -> relation.setMainClass(RelationClass.UNDEFINED));
    }
}
