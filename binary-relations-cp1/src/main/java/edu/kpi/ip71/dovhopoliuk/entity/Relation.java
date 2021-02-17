package edu.kpi.ip71.dovhopoliuk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.INTEGER_ZERO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relation {

    private List<String> elements;
    private List<List<Boolean>> matrix;
    private Set<RelationProperty> properties;
    private List<RelationPropertyViolation> violations;

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
        matrix.forEach(System.out::println);

        System.out.println("Properties: " + properties.toString());

        System.out.println("Violations: ");

        violations
                .forEach(violation -> {
                    System.out.println(violation.getProperty().name());
                    violation.getMessages().forEach(System.out::println);
                });
    }
}
