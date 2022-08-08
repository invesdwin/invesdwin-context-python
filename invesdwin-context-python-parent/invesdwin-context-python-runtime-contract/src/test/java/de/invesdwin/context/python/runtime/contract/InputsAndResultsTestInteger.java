package de.invesdwin.context.python.runtime.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.collections.Arrays;

@NotThreadSafe
public class InputsAndResultsTestInteger {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestInteger(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testInteger() {
        //putInteger
        final int putInteger = 123;

        //putIntegerVector
        final int[] putIntegerVector = new int[3];
        for (int i = 0; i < putIntegerVector.length; i++) {
            putIntegerVector[i] = Integer.parseInt((i + 1) + "" + (i + 1));
        }

        //putIntegerVectorAsList
        final List<Integer> putIntegerVectorAsList = Arrays.asList(Arrays.toObject(putIntegerVector));

        //putIntegerMatrix
        final int[][] putIntegerMatrix = new int[4][];
        for (int row = 0; row < putIntegerMatrix.length; row++) {
            final int[] vector = new int[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = Integer.parseInt((row + 1) + "" + (col + 1));
            }
            putIntegerMatrix[row] = vector;
        }

        //putIntegerMatrixAsList
        final List<List<Integer>> putIntegerMatrixAsList = new ArrayList<List<Integer>>(putIntegerMatrix.length);
        for (final int[] vector : putIntegerMatrix) {
            putIntegerMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putInteger("putInteger", putInteger);

                inputs.putIntegerVector("putIntegerVector", putIntegerVector);

                inputs.putIntegerVectorAsList("putIntegerVectorAsList", putIntegerVectorAsList);

                inputs.putIntegerMatrix("putIntegerMatrix", putIntegerMatrix);

                inputs.putIntegerMatrixAsList("putIntegerMatrixAsList", putIntegerMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestInteger.class.getSimpleName() + ".py",
                        InputsAndResultsTestInteger.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getInteger
                final Integer getInteger = results.getInteger("getInteger");
                Assertions.assertThat(putInteger).isEqualTo(getInteger);

                //getIntegerVector
                final int[] getIntegerVector = results.getIntegerVector("getIntegerVector");
                Assertions.assertThat(putIntegerVector).isEqualTo(getIntegerVector);

                //getIntegerVectorAsList
                final List<Integer> getIntegerVectorAsList = results.getIntegerVectorAsList("getIntegerVectorAsList");
                Assertions.assertThat(putIntegerVectorAsList).isEqualTo(getIntegerVectorAsList);

                //getIntegerMatrix
                final int[][] getIntegerMatrix = results.getIntegerMatrix("getIntegerMatrix");
                Assertions.assertThat(putIntegerMatrix).isEqualTo(getIntegerMatrix);

                //getIntegerMatrixAsList
                final List<List<Integer>> getIntegerMatrixAsList = results
                        .getIntegerMatrixAsList("getIntegerMatrixAsList");
                Assertions.assertThat(putIntegerMatrixAsList).isEqualTo(getIntegerMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
