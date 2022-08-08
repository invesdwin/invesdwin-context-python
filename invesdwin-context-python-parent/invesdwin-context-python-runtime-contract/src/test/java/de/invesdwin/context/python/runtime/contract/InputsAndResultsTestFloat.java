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
public class InputsAndResultsTestFloat {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestFloat(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testFloat() {
        //putFloat
        final float putFloat = 123.123F;

        //putFloatVector
        final float[] putFloatVector = new float[3];
        for (int i = 0; i < putFloatVector.length; i++) {
            putFloatVector[i] = Float.parseFloat((i + 1) + "." + (i + 1));
        }

        //putFloatVectorAsList
        final List<Float> putFloatVectorAsList = Arrays.asList(Arrays.toObject(putFloatVector));

        //putFloatMatrix
        final float[][] putFloatMatrix = new float[4][];
        for (int row = 0; row < putFloatMatrix.length; row++) {
            final float[] vector = new float[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = Float.parseFloat((row + 1) + "." + (col + 1));
            }
            putFloatMatrix[row] = vector;
        }

        //putFloatMatrixAsList
        final List<List<Float>> putFloatMatrixAsList = new ArrayList<List<Float>>(putFloatMatrix.length);
        for (final float[] vector : putFloatMatrix) {
            putFloatMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putFloat("putFloat", putFloat);

                inputs.putFloatVector("putFloatVector", putFloatVector);

                inputs.putFloatVectorAsList("putFloatVectorAsList", putFloatVectorAsList);

                inputs.putFloatMatrix("putFloatMatrix", putFloatMatrix);

                inputs.putFloatMatrixAsList("putFloatMatrixAsList", putFloatMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestFloat.class.getSimpleName() + ".py",
                        InputsAndResultsTestFloat.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getFloat
                final Float getFloat = results.getFloat("getFloat");
                Assertions.assertThat(putFloat).isEqualTo(getFloat);

                //getFloatVector
                final float[] getFloatVector = results.getFloatVector("getFloatVector");
                Assertions.assertThat(putFloatVector).isEqualTo(getFloatVector);

                //getFloatVectorAsList
                final List<Float> getFloatVectorAsList = results.getFloatVectorAsList("getFloatVectorAsList");
                Assertions.assertThat(putFloatVectorAsList).isEqualTo(getFloatVectorAsList);

                //getFloatMatrix
                final float[][] getFloatMatrix = results.getFloatMatrix("getFloatMatrix");
                Assertions.assertThat(putFloatMatrix).isEqualTo(getFloatMatrix);

                //getFloatMatrixAsList
                final List<List<Float>> getFloatMatrixAsList = results.getFloatMatrixAsList("getFloatMatrixAsList");
                Assertions.assertThat(putFloatMatrixAsList).isEqualTo(getFloatMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
