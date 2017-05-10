package de.invesdwin.context.r.runtime.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class InputsAndResultsTestDouble {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestDouble(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testDouble() {
        //putDouble
        final double putDouble = 123.123D;

        //putDoubleVector
        final double[] putDoubleVector = new double[3];
        for (int i = 0; i < putDoubleVector.length; i++) {
            putDoubleVector[i] = Double.parseDouble((i + 1) + "." + (i + 1));
        }

        //putDoubleVectorAsList
        final List<Double> putDoubleVectorAsList = Arrays.asList(ArrayUtils.toObject(putDoubleVector));

        //putDoubleMatrix
        final double[][] putDoubleMatrix = new double[4][];
        for (int row = 0; row < putDoubleMatrix.length; row++) {
            final double[] vector = new double[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = Double.parseDouble((row + 1) + "." + (col + 1));
            }
            putDoubleMatrix[row] = vector;
        }

        //putDoubleMatrixAsList
        final List<List<Double>> putDoubleMatrixAsList = new ArrayList<List<Double>>(putDoubleMatrix.length);
        for (final double[] vector : putDoubleMatrix) {
            putDoubleMatrixAsList.add(Arrays.asList(ArrayUtils.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public Resource getScriptResource() {
                return new ClassPathResource(InputsAndResultsTestDouble.class.getSimpleName() + ".py",
                        InputsAndResultsTestDouble.class);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putDouble("putDouble", putDouble);

                inputs.putDoubleVector("putDoubleVector", putDoubleVector);

                inputs.putDoubleVectorAsList("putDoubleVectorAsList", putDoubleVectorAsList);

                inputs.putDoubleMatrix("putDoubleMatrix", putDoubleMatrix);

                inputs.putDoubleMatrixAsList("putDoubleMatrixAsList", putDoubleMatrixAsList);
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
