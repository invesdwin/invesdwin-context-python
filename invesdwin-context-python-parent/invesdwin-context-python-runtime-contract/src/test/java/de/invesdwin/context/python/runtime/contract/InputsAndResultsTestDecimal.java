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
import de.invesdwin.util.math.decimal.Decimal;

@NotThreadSafe
public class InputsAndResultsTestDecimal {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestDecimal(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testDecimal() {
        //putDecimal
        final Decimal putDecimal = new Decimal("123.123");

        //putDecimalVector
        final Decimal[] putDecimalVector = new Decimal[3];
        for (int i = 0; i < putDecimalVector.length; i++) {
            putDecimalVector[i] = Decimal.valueOf((i + 1) + "." + (i + 1));
        }

        //putDecimalVectorAsList
        final List<Decimal> putDecimalVectorAsList = Arrays.asList(putDecimalVector);

        //putDecimalMatrix
        final Decimal[][] putDecimalMatrix = new Decimal[4][];
        for (int row = 0; row < putDecimalMatrix.length; row++) {
            final Decimal[] vector = new Decimal[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = Decimal.valueOf((row + 1) + "." + (col + 1));
            }
            putDecimalMatrix[row] = vector;
        }

        //putDecimalMatrixAsList
        final List<List<Decimal>> putDecimalMatrixAsList = new ArrayList<List<Decimal>>(putDecimalMatrix.length);
        for (final Decimal[] vector : putDecimalMatrix) {
            putDecimalMatrixAsList.add(Arrays.asList(vector));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putDecimal("putDecimal", putDecimal);

                inputs.putDecimalVector("putDecimalVector", putDecimalVector);

                inputs.putDecimalVectorAsList("putDecimalVectorAsList", putDecimalVectorAsList);

                inputs.putDecimalMatrix("putDecimalMatrix", putDecimalMatrix);

                inputs.putDecimalMatrixAsList("putDecimalMatrixAsList", putDecimalMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestDecimal.class.getSimpleName() + ".py",
                        InputsAndResultsTestDecimal.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getDecimal
                final Decimal getDecimal = results.getDecimal("getDecimal");
                Assertions.assertThat(putDecimal).isEqualTo(getDecimal);

                //getDecimalVector
                final Decimal[] getDecimalVector = results.getDecimalVector("getDecimalVector");
                Assertions.assertThat(putDecimalVector).isEqualTo(getDecimalVector);

                //getDecimalVectorAsList
                final List<Decimal> getDecimalVectorAsList = results.getDecimalVectorAsList("getDecimalVectorAsList");
                Assertions.assertThat(putDecimalVectorAsList).isEqualTo(getDecimalVectorAsList);

                //getDecimalMatrix
                final Decimal[][] getDecimalMatrix = results.getDecimalMatrix("getDecimalMatrix");
                Assertions.assertThat(putDecimalMatrix).isEqualTo(getDecimalMatrix);

                //getDecimalMatrixAsList
                final List<List<Decimal>> getDecimalMatrixAsList = results
                        .getDecimalMatrixAsList("getDecimalMatrixAsList");
                Assertions.assertThat(putDecimalMatrixAsList).isEqualTo(getDecimalMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
