package edu.kpi.ip71.dovhopoliuk.common.controller;

import edu.kpi.ip71.dovhopoliuk.common.entity.CriteriaInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.ElectreInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.FuzzyRelation;
import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import edu.kpi.ip71.dovhopoliuk.common.entity.TopsisInfo;
import edu.kpi.ip71.dovhopoliuk.common.entity.VikorInfo;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.CHAR_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.EMPTY;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_THREE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_TWO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.SPACE;

public class ReadInputController {

    @SneakyThrows
    public List<Relation> readInput(final String inputFilePath) {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(inputFilePath))
                .map(this::readInputForUrl)
                .orElse(Collections.emptyList());
    }

    @SneakyThrows
    public List<FuzzyRelation> readFuzzyInput(final String inputFilePath) {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(inputFilePath))
                .map(this::readFuzzyInputForUrl)
                .orElse(Collections.emptyList());
    }

    @SneakyThrows
    public CriteriaInfo readCriteriaInput(final String inputFilePath) {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(inputFilePath))
                .map(this::readCriteriaInputForUrl)
                .orElse(null);
    }

    public ElectreInfo readElectreInput(final String inputFilePath) {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(inputFilePath))
                .map(this::readElectreInputForUrl)
                .orElse(null);
    }

    public TopsisInfo readTopsisInput(final String inputFilePath) {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(inputFilePath))
                .map(this::readTopsisInputForUrl)
                .orElse(null);
    }

    public VikorInfo readVikorInput(final String inputFilePath) {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(inputFilePath))
                .map(this::readVikorInputForUrl)
                .orElse(null);
    }


    @SneakyThrows
    private List<Relation> readInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        final List<List<String>> relations = new ArrayList<>();

        List<String> currentRelation = new ArrayList<>();

        for (final String line : lines) {

            if (line.startsWith("№") || line.startsWith("R")) {

                currentRelation = new ArrayList<>();
                relations.add(currentRelation);

            } else {

                currentRelation.add(line);
            }
        }

        return IntStream.range(INTEGER_ZERO, relations.size())
                .mapToObj(index -> parseRelation(relations.get(index), index))
                .collect(Collectors.toList());
    }

    private Relation parseRelation(final List<String> relation, final int relationNumber) {

        return Relation.builder()
                .relationName("R" + (relationNumber + INTEGER_ONE))
                .matrix(parseRelationMatrix(relation))
                .elements(generateElementNames(relation.size()))
                .properties(new HashSet<>())
                .classes(new HashSet<>())
                .build();
    }

    @SneakyThrows
    private List<FuzzyRelation> readFuzzyInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        final List<List<String>> relations = new ArrayList<>();

        List<String> currentRelation = new ArrayList<>();

        for (final String line : lines) {

            if (line.startsWith("№") || line.startsWith("R")) {

                currentRelation = new ArrayList<>();
                relations.add(currentRelation);

            } else {

                currentRelation.add(line);
            }
        }

        return IntStream.range(INTEGER_ZERO, relations.size())
                .mapToObj(index -> parseFuzzyRelation(relations.get(index), index))
                .collect(Collectors.toList());
    }

    private FuzzyRelation parseFuzzyRelation(final List<String> relation, final int relationNumber) {

        return FuzzyRelation.builder()
                .relationName("R" + (relationNumber + INTEGER_ONE))
                .matrix(parseFuzzyRelationMatrix(relation))
                .elements(generateElementNames(relation.size()))
                .properties(new HashSet<>())
                .build();
    }

    private List<List<Boolean>> parseRelationMatrix(final List<String> matrix) {

        return matrix.stream()
                .map(this::parseRelationMatrixLine)
                .collect(Collectors.toList());
    }

    private List<List<Double>> parseFuzzyRelationMatrix(final List<String> matrix) {

        return matrix.stream()
                .map(this::parseFuzzyRelationMatrixLine)
                .collect(Collectors.toList());
    }

    private List<Boolean> parseRelationMatrixLine(final String line) {

        return line.replaceAll(SPACE, EMPTY)
                .chars()
                .mapToObj(number -> (char) number)
                .map(number -> number == CHAR_ONE)
                .collect(Collectors.toList());
    }

    private List<Double> parseFuzzyRelationMatrixLine(final String line) {

        return Arrays.stream(line.trim()
                .split(" "))
                .filter(str -> !str.equals(""))
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

    private List<String> generateElementNames(final int size) {

        return IntStream.range(INTEGER_ONE, size + INTEGER_ONE)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private CriteriaInfo readCriteriaInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        List<List<String>> groups = lines.stream()
                .reduce(createIdentity(),
                        this::combineElementForGroupsReduce,
                        (acc1, acc2) -> acc1);


        return CriteriaInfo.builder()
                .marks(createMarks(groups.get(INTEGER_ZERO)))
                .strictOrderOfImportance(createStrictOrderOfImportance(groups.get(INTEGER_ONE)))
                .invertedQuasiOrderOfImportance(createQuasiOrderOfImportance(groups.get(INTEGER_TWO)))
                .build();
    }

    @SneakyThrows
    private ElectreInfo readElectreInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        List<List<String>> groups = lines.stream()
                .reduce(createIdentity(),
                        this::combineElementForGroupsReduce,
                        (acc1, acc2) -> acc1);

        return ElectreInfo.builder()
                .marks(createElectreMarks(groups.get(INTEGER_ZERO)))
                .weights(createWeights(groups.get(INTEGER_ONE)))
                .c(createC(groups.get(INTEGER_TWO)))
                .d(createD(groups.get(INTEGER_TWO)))
                .build();
    }

    @SneakyThrows
    private TopsisInfo readTopsisInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        List<List<String>> groups = lines.stream()
                .reduce(createIdentity(),
                        this::combineElementForGroupsReduce,
                        (acc1, acc2) -> acc1);

        return TopsisInfo.builder()
                .marks(createElectreMarks(groups.get(INTEGER_ZERO)))
                .weights(createWeights(groups.get(INTEGER_ONE)))
                .criteriaToMaximize(createCriteria(groups.get(INTEGER_TWO)))
                .criteriaToMinimize(createCriteria(groups.get(INTEGER_THREE)))
                .build();
    }

    @SneakyThrows
    private VikorInfo readVikorInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        List<List<String>> groups = lines.stream()
                .reduce(createIdentity(),
                        this::combineElementForGroupsReduce,
                        (acc1, acc2) -> acc1);

        return VikorInfo.builder()
                .marks(createElectreMarks(groups.get(INTEGER_ZERO)))
                .weights(createWeights(groups.get(INTEGER_ONE)))
                .v(createV(groups.get(INTEGER_TWO)))
                .build();
    }

    private List<List<String>> combineElementForGroupsReduce(final List<List<String>> accumulator, final String element) {

        if (element.isEmpty()) {

            accumulator.add(new ArrayList<>());

        } else {

            accumulator.get(accumulator.size() - INTEGER_ONE).add(element);
        }

        return accumulator;
    }

    private List<List<String>> createIdentity() {

        final List<List<String>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        return result;
    }

    private List<List<Integer>> createMarks(final List<String> marks) {

        return marks.stream()
                .map(this::createMarksMatrixLine)
                .collect(Collectors.toList());
    }

    private List<Integer> createMarksMatrixLine(final String marks) {

        return Arrays.stream(marks.trim().split(" "))
                .filter(Predicate.not(String::isEmpty))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<Integer> createStrictOrderOfImportance(final List<String> sequence) {

        return Optional.ofNullable(sequence)
                .map(element -> element.get(INTEGER_ZERO))
                .map(element -> Arrays.stream(element.split(">"))
                        .map(criteria -> Integer.parseInt(criteria.replace("k", "")) - INTEGER_ONE)
                        .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    private List<Set<Integer>> createQuasiOrderOfImportance(final List<String> sequence) {

        return Optional.ofNullable(sequence)
                .map(element -> element.get(INTEGER_ZERO))
                .map(element -> Arrays.stream(element.split("<"))
                        .map(criteriaSet -> createCriteriaSet(criteriaSet.trim()))
                        .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    private Set<Integer> createCriteriaSet(final String set) {

        return Arrays.stream(set.replace("{", "")
                .replace("}", "")
                .split(","))
                .map(element -> Integer.parseInt(element.replace("k", "")) - INTEGER_ONE)
                .collect(Collectors.toSet());
    }

    private List<List<Integer>> createElectreMarks(final List<String> marks) {

        return createMarks(marks.subList(INTEGER_ONE, marks.size()));
    }

    private List<Double> createWeights(final List<String> weights) {

        return Arrays.stream(weights.get(INTEGER_ONE).trim().split(" "))
                .filter(Predicate.not(String::isEmpty))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    private double createC(final List<String> lines) {

        return Double.parseDouble(lines.get(INTEGER_ONE).split(" ")[INTEGER_ZERO]);
    }

    private double createD(final List<String> lines) {

        return Double.parseDouble(lines.get(INTEGER_ONE).split(" ")[INTEGER_ONE]);
    }

    private List<Integer> createCriteria(final List<String> lines) {

        return Arrays.stream(lines.get(INTEGER_ONE).split(SPACE))
                .map(Integer::valueOf)
                .map(value -> value - INTEGER_ONE)
                .collect(Collectors.toList());
    }

    private double createV(final List<String> lines) {

        return Double.parseDouble(lines.get(INTEGER_ONE));
    }
}
