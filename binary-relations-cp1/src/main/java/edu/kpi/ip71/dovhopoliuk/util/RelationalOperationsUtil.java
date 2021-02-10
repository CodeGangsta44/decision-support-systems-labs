package edu.kpi.ip71.dovhopoliuk.util;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;

import java.util.List;
import java.util.stream.Collectors;

public class RelationalOperationsUtil {

    public static Relation invert(final Relation relation) {

        return Relation.builder()
                .elements(relation.getElements())
                .matrix(invertMatrix(relation.getMatrix()))
                .build();
    }

    private static List<List<Boolean>> invertMatrix(final List<List<Boolean>> matrix) {

        return matrix.stream()
                .map(row -> row.stream()
                        .map(element -> !element)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
