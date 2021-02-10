package edu.kpi.ip71.dovhopoliuk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    public Boolean getElement(final int rowIndex, final int columnIndex) {

        return matrix.get(rowIndex).get(columnIndex);
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
}
