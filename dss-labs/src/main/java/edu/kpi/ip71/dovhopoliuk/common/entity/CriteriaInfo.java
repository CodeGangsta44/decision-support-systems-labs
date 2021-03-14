package edu.kpi.ip71.dovhopoliuk.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriaInfo {

    private List<List<Integer>> marks;
    private List<Integer> strictOrderOfImportance;
    private List<Set<Integer>> invertedQuasiOrderOfImportance;

    private Integer quantity;

    public int getQuantityOfCriteria() {

        return Optional.ofNullable(quantity)
                .orElseGet(this::calculateQuantityOfCriteria);
    }

    public int getMark(final int element, final int criteria) {

        return marks.get(element).get(criteria);
    }

    public void print() {

        System.out.println("Marks: ");
        marks.forEach(System.out::println);

        System.out.println("\nStrict order of importance: ");
        System.out.println(strictOrderOfImportance);

        System.out.println("\nQuasi order of importance: ");
        System.out.println(invertedQuasiOrderOfImportance);
    }

    private int calculateQuantityOfCriteria() {

        quantity = marks.stream()
                .findAny()
                .map(List::size)
                .orElse(INTEGER_ZERO);

        return quantity;
    }
}
