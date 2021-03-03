package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.constants.Constants.INTEGER_ZERO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relation {

    private List<String> elements;
    private List<List<Boolean>> matrix;
    private Set<RelationProperty> properties;
    private List<RelationPropertyViolation> violations;
    private Set<RelationClass> classes;

    public Boolean getElement(final int rowIndex, final int columnIndex) {

        return matrix.get(rowIndex).get(columnIndex);
    }

    public String getElementName(final int index) {

        return elements.get(index);
    }

    public int getSize() {

        return matrix.size();
    }

    public List<Integer> getLowerSection(final int element) {

        return IntStream.range(INTEGER_ZERO, getSize())
                .filter(index -> getElement(element, index))
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Integer> getUpperSection(final int element) {

        return IntStream.range(INTEGER_ZERO, getSize())
                .filter(index -> getElement(index, element))
                .boxed()
                .collect(Collectors.toList());
    }

    public void print() {

        System.out.println();
        System.out.println();
        System.out.println("Matrix:");

        matrix.stream()
                .map(row -> row.stream().map(element -> element ? "true " : "false").collect(Collectors.toList()))
                .forEach(System.out::println);
//        matrix.forEach(System.out::println);

        System.out.println("Properties: " + properties.toString());

        System.out.println("\nViolations: ");

        violations
                .forEach(violation -> {
                    System.out.println("\n" + violation.getProperty().name() + " VIOLATIONS:");
                    violation.getMessages()
                            .forEach(System.out::println);
                });

//        System.out.println("\nAll classes: ");
//
//        classes.stream()
//                .filter(Predicate.not(RelationClass.UNDEFINED::equals))
//                .forEach(System.out::println);
    }
}
