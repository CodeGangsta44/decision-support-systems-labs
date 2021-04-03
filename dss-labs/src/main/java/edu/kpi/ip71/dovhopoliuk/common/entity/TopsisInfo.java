package edu.kpi.ip71.dovhopoliuk.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopsisInfo {

    private List<List<Integer>> marks;
    private List<Double> weights;
    private List<Integer> criteriaToMaximize;
    private List<Integer> criteriaToMinimize;

    public int getQuantityOfCriteria() {

        return weights.size();
    }

    public int getSize() {

        return marks.size();
    }

    public double getMark(final int rowIndex, final int columnIndex) {

        return marks.get(rowIndex).get(columnIndex);
    }
}
