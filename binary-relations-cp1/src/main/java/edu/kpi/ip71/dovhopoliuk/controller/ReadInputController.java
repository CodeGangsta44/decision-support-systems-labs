package edu.kpi.ip71.dovhopoliuk.controller;

import edu.kpi.ip71.dovhopoliuk.entity.Relation;
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

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.CHAR_ONE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.EMPTY;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SPACE;

public class ReadInputController {

    private static final String INPUT_FILE_PATH = "input.txt";

    @SneakyThrows
    public List<Relation> readInput() {

        return Optional.ofNullable(getClass().getClassLoader()
                .getResource(INPUT_FILE_PATH))
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

            if (line.startsWith("№")) {

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
                .elements(List.of("1", "2", "3", "4", "5", "6"))
                .properties(new HashSet<>())
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
}
