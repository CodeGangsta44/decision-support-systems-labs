package edu.kpi.ip71.dovhopoliuk.common.util;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RelationalOperationsUtil {

    public static Relation invert(final Relation relation) {

        return Relation.builder()
                .elements(relation.getElements())
                .matrix(invertMatrix(relation.getMatrix()))
                .build();
    }

    public static Set<Integer> removeElementFromSet(final Set<Integer> set, final int element) {

        return set.stream()
                .filter(member -> !member.equals(element))
                .collect(Collectors.toSet());
    }

    public static Set<Integer> intersectCollections(final Collection<Integer> set1, final Collection<Integer> set2) {

        return set1.stream()
                .filter(set2::contains)
                .collect(Collectors.toSet());
    }

    public static int sign(final int value) {

        return Integer.compare(value, 0);
    }

    private static List<List<Boolean>> invertMatrix(final List<List<Boolean>> matrix) {

        return matrix.stream()
                .map(row -> row.stream()
                        .map(element -> !element)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
