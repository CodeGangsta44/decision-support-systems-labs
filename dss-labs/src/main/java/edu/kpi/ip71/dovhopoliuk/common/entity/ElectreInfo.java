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
public class ElectreInfo {

    private List<List<Integer>> marks;
    private List<Double> weights;
    private double c;
    private double d;

    private List<List<Double>> concordanceMatrix;
    private List<List<Double>> discordanceMatrix;

    public int getSize() {

        return marks.size();
    }

    public int getMark(final int rowIndex, final int columnIndex) {

        return marks.get(rowIndex).get(columnIndex);
    }

    public double getWeight(final int index) {

        return weights.get(index);
    }

    public double getConcordanceIndex(final int rowIndex, final int columnIndex) {

        return concordanceMatrix.get(rowIndex).get(columnIndex);
    }

    public double getDiscordanceIndex(final int rowIndex, final int columnIndex) {

        return discordanceMatrix.get(rowIndex).get(columnIndex);
    }
}
