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
public class InputsAndResultsTestDoubleNan {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestDoubleNan(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testDoubleNan() {
        //putDouble
        final double putDouble = Double.NaN;

        //putDoubleVector
        final double[] putDoubleVector = new double[3];
        for (int i = 0; i < putDoubleVector.length; i++) {
            putDoubleVector[i] = Double.parseDouble((i + 1) + "." + (i + 1));
        }
        putDoubleVector[1] = Double.NaN;

        //putDoubleVectorAsList
        final List<Double> putDoubleVectorAsList = Arrays.asList(Arrays.toObject(putDoubleVector));

        //putDoubleMatrix
        final double[][] putDoubleMatrix = new double[4][];
        for (int row = 0; row < putDoubleMatrix.length; row++) {
            final double[] vector = new double[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = Double.parseDouble((row + 1) + "." + (col + 1));
            }
            putDoubleMatrix[row] = vector;
        }
        putDoubleMatrix[0][0] = Double.NaN;
        putDoubleMatrix[1][1] = Double.NaN;
        putDoubleMatrix[2][2] = Double.NaN;

        //putDoubleMatrixAsList
        final List<List<Double>> putDoubleMatrixAsList = new ArrayList<List<Double>>(putDoubleMatrix.length);
        for (final double[] vector : putDoubleMatrix) {
            putDoubleMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putDouble("putDouble", putDouble);

                inputs.putDoubleVector("putDoubleVector", putDoubleVector);

                inputs.putDoubleVectorAsList("putDoubleVectorAsList", putDoubleVectorAsList);

                inputs.putDoubleMatrix("putDoubleMatrix", putDoubleMatrix);

                inputs.putDoubleMatrixAsList("putDoubleMatrixAsList", putDoubleMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestDoubleNan.class.getSimpleName() + ".py",
                        InputsAndResultsTestDoubleNan.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getDouble
                final Double getDouble = results.getDouble("getDouble");
                Assertions.assertThat(putDouble).isEqualTo(getDouble);

                //getDoubleVector
                final double[] getDoubleVector = results.getDoubleVector("getDoubleVector");
                Assertions.assertThat(putDoubleVector).isEqualTo(getDoubleVector);

                //getDoubleVectorAsList
                final List<Double> getDoubleVectorAsList = results.getDoubleVectorAsList("getDoubleVectorAsList");
                Assertions.assertThat(putDoubleVectorAsList).isEqualTo(getDoubleVectorAsList);

                //getDoubleMatrix
                final double[][] getDoubleMatrix = results.getDoubleMatrix("getDoubleMatrix");
                Assertions.assertThat(putDoubleMatrix).isEqualTo(getDoubleMatrix);

                //getDoubleMatrixAsList
                final List<List<Double>> getDoubleMatrixAsList = results.getDoubleMatrixAsList("getDoubleMatrixAsList");
                Assertions.assertThat(putDoubleMatrixAsList).isEqualTo(getDoubleMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
