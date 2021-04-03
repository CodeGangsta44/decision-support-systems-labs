package edu.kpi.ip71.dovhopoliuk.cp5.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.SPACE;

public class PrintUtils {

    private static final String FORMATTER_PATTERN = "0.000";
    private static final DecimalFormat FORMATTER = new DecimalFormat(FORMATTER_PATTERN);

    static {

        FORMATTER.setRoundingMode(RoundingMode.HALF_UP);
    }

    public static void printTitleAndDoubleMatrix(final String title, final List<List<Double>> matrix) {

        System.out.println(title);

        matrix.stream()
                .map(row -> row.stream()
                        .map(FORMATTER::format)
                        .collect(Collectors.joining(SPACE)))
                .forEach(System.out::println);

        System.out.println();
    }

    public static void printTitleAndIntegerMatrix(final String title, final List<List<Integer>> matrix) {

        System.out.println(title);

        matrix.stream()
                .map(row -> row.stream()
                        .map(value -> String.format("%2d", value))
                        .collect(Collectors.joining(SPACE)))
                .forEach(System.out::println);

        System.out.println();
    }

    public static void printTitleAndDoubleList(final String title, final List<Double> list) {

        System.out.println(title + SPACE + list.stream().map(FORMATTER::format).collect(Collectors.toList()));
    }

    public static void printTitleAndIntegerList(final String title, final List<Integer> list) {

        System.out.println(title + SPACE + list.toString());
    }

    public static void printTitleAndMap(final String title, final Map<Integer, Double> map) {

        printTitleAndDoubleList(title, map.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }

    public static void printTitleAndMapWithIndexes(final String title, final Map<Integer, Double> map) {

        printTitleAndListOfEntries(title, map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList()));
    }

    public static void printTitleAndListOfEntries(final String title, final List<Map.Entry<Integer, Double>> list) {

        final List<String> result = list.stream()
                .map(entry -> "A" + (entry.getKey() + INTEGER_ONE) + " = " + FORMATTER.format(entry.getValue()))
                .collect(Collectors.toList());

        System.out.println(title + result.toString());
    }
}


