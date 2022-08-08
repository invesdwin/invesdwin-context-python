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
public class InputsAndResultsTestShort {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestShort(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testShort() {
        //putShort
        final short putShort = 123;

        //putShortVector
        final short[] putShortVector = new short[3];
        for (int i = 0; i < putShortVector.length; i++) {
            putShortVector[i] = Short.parseShort((i + 1) + "" + (i + 1));
        }

        //putShortVectorAsList
        final List<Short> putShortVectorAsList = Arrays.asList(Arrays.toObject(putShortVector));

        //putShortMatrix
        final short[][] putShortMatrix = new short[4][];
        for (int row = 0; row < putShortMatrix.length; row++) {
            final short[] vector = new short[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = Short.parseShort((row + 1) + "" + (col + 1));
            }
            putShortMatrix[row] = vector;
        }

        //putShortMatrixAsList
        final List<List<Short>> putShortMatrixAsList = new ArrayList<List<Short>>(putShortMatrix.length);
        for (final short[] vector : putShortMatrix) {
            putShortMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putShort("putShort", putShort);

                inputs.putShortVector("putShortVector", putShortVector);

                inputs.putShortVectorAsList("putShortVectorAsList", putShortVectorAsList);

                inputs.putShortMatrix("putShortMatrix", putShortMatrix);

                inputs.putShortMatrixAsList("putShortMatrixAsList", putShortMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestShort.class.getSimpleName() + ".py",
                        InputsAndResultsTestShort.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getShort
                final Short getShort = results.getShort("getShort");
                Assertions.assertThat(putShort).isEqualTo(getShort);

                //getShortVector
                final short[] getShortVector = results.getShortVector("getShortVector");
                Assertions.assertThat(putShortVector).isEqualTo(getShortVector);

                //getShortVectorAsList
                final List<Short> getShortVectorAsList = results.getShortVectorAsList("getShortVectorAsList");
                Assertions.assertThat(putShortVectorAsList).isEqualTo(getShortVectorAsList);

                //getShortMatrix
                final short[][] getShortMatrix = results.getShortMatrix("getShortMatrix");
                Assertions.assertThat(putShortMatrix).isEqualTo(getShortMatrix);

                //getShortMatrixAsList
                final List<List<Short>> getShortMatrixAsList = results.getShortMatrixAsList("getShortMatrixAsList");
                Assertions.assertThat(putShortMatrixAsList).isEqualTo(getShortMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
