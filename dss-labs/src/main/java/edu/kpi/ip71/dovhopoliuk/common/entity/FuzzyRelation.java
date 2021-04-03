package edu.kpi.ip71.dovhopoliuk.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuzzyRelation {

    private String relationName;
    private List<String> elements;
    private List<List<Double>> matrix;
    private List<FuzzyRelationPropertyViolation> violations;
    private Set<FuzzyRelationProperty> properties;

    public int getSize() {

        return matrix.size();
    }

    public Double getElement(final int rowIndex, final int columnIndex) {

        return matrix.get(rowIndex).get(columnIndex);
    }

    public String getElementName(final int index) {

        return elements.get(index);
    }

    public void print() {

        final DecimalFormat format = new DecimalFormat("0.000");
        format.setRoundingMode(RoundingMode.HALF_UP);


        System.out.println();
        System.out.println();
        System.out.println("Relation " + relationName);
        System.out.println("Matrix:");

        matrix.stream()
                .map(row -> row.stream().map(format::format).collect(Collectors.toList()))
                .forEach(System.out::println);

        Optional.ofNullable(properties)
                .filter(properties -> !properties.isEmpty())
                .ifPresent(prop -> System.out.println("Properties: " + prop.toString()));

        Optional.ofNullable(violations)
                .filter(violations -> !violations.isEmpty())
                .ifPresent(this::printViolations);
    }

    private void printViolations(final List<FuzzyRelationPropertyViolation> violations) {

        System.out.println("\nViolations: ");

        violations
                .forEach(violation -> {

                    Optional.ofNullable(violation.getMessages())
                            .filter(Predicate.not(Collection::isEmpty))
                            .ifPresent(messages -> {
                                System.out.println("\n" + violation.getProperty().name() + " VIOLATIONS:");
                                messages.forEach(System.out::println);
                            });
                });
    }
}
