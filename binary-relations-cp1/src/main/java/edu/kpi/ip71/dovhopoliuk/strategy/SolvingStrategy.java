package edu.kpi.ip71.dovhopoliuk.strategy;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.entity.RelationClass;
import edu.kpi.ip71.dovhopoliuk.entity.RelationProperty;

import java.util.Arrays;
import java.util.Comparator;

public class SolvingStrategy {

    private final PropertiesDerivationStrategy propertiesDerivationStrategy;

    public SolvingStrategy(final PropertiesDerivationStrategy propertiesDerivationStrategy) {

        this.propertiesDerivationStrategy = propertiesDerivationStrategy;
    }

    public RelationClass solve(final Relation relation) {

        analyzeProperties(relation);

        derivateProperties(relation);

        return determineClass(relation);
    }

    private void analyzeProperties(final Relation relation) {

        Arrays.stream(RelationProperty.values())
                .filter(property -> property.test(relation))
                .forEach(property -> relation.getProperties().add(property));
    }

    private void derivateProperties(final Relation relation) {

        propertiesDerivationStrategy.derivate(relation);
    }

    private RelationClass determineClass(final Relation relation) {

        return Arrays.stream(RelationClass.values())
                .filter(relationClass -> relationClass.test(relation))
                .min(Comparator.comparingInt(Enum::ordinal))
                .orElse(RelationClass.UNDEFINED);
    }
}
