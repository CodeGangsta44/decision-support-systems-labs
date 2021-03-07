package edu.kpi.ip71.dovhopoliuk.common.controller;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.CHAR_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.EMPTY;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
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
    private List<Relation> readInputForUrl(final URL url) {

        final Path path = Paths.get(url.toURI());

        final List<String> lines = Files.readAllLines(path);

        final List<List<String>> relations = new ArrayList<>();

        List<String> currentRelation = new ArrayList<>();

        for (final String line : lines) {

            if (line.startsWith("№")  || line.startsWith("R")) {

                currentRelation = new ArrayList<>();
                relations.add(currentRelation);

            } else {

                currentRelation.add(line);
            }
        }

        return relations.stream()
                .map(this::parseRelation)
                .collect(Collectors.toList());
    }

    private Relation parseRelation(final List<String> relation) {

        return Relation.builder()
                .matrix(parseRelationMatrix(relation))
                .elements(generateElementNames(relation.size()))
                .properties(new HashSet<>())
                .classes(new HashSet<>())
                .build();
    }

    private List<List<Boolean>> parseRelationMatrix(final List<String> matrix) {

        return matrix.stream()
                .map(this::parseRelationMatrixLine)
                .collect(Collectors.toList());
    }

    private List<Boolean> parseRelationMatrixLine(final String line) {

        return line.replaceAll(SPACE, EMPTY)
                .chars()
                .mapToObj(number -> (char) number)
                .map(number -> number == CHAR_ONE)
                .collect(Collectors.toList());
    }

    private List<String> generateElementNames(final int size) {

        return IntStream.range(INTEGER_ONE, size + INTEGER_ONE)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }
}
