package edu.kpi.ip71.dovhopoliuk.common.controller;

import edu.kpi.ip71.dovhopoliuk.common.entity.Relation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.common.constants.Constants.INTEGER_ZERO;

public class WriteOutputController {

    public void saveRelations(final String path, final List<Relation> result) {

        writeToFile(path, createResultString(result));
    }

    public void saveResultString(final String path, final String result) {

        writeToFile(path, result);
    }

    private String createResultString(final List<Relation> relations) {

        final StringBuilder stringBuilder = new StringBuilder();

        IntStream.range(INTEGER_ZERO, relations.size())
                .forEach(index -> stringBuilder.append(" ").append(index + INTEGER_ONE).append("\n").append(createRelationMatrixString(relations.get(index))));

        return stringBuilder.toString();
    }

    private String createRelationMatrixString(final Relation relation) {

        final StringBuilder stringBuilder = new StringBuilder();

        relation.getMatrix().stream()
                .map(line -> line.stream()
                        .map(element -> String.valueOf(element ? INTEGER_ONE : INTEGER_ZERO))
                        .collect(Collectors.joining("  ")))
                .forEach(line -> stringBuilder.append(" ").append(line).append("\n"));

        return stringBuilder.toString();
    }

    private void writeToFile(final String path, final String result) {

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            writer.append(result);

        } catch (final IOException e) {

            e.printStackTrace();
        }
    }
}
