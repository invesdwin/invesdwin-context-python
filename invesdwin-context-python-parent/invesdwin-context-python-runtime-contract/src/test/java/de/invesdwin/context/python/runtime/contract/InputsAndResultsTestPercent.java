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
import de.invesdwin.util.math.decimal.scaled.Percent;
import de.invesdwin.util.math.decimal.scaled.PercentScale;

@NotThreadSafe
public class InputsAndResultsTestPercent {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestPercent(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testPercent() {
        //putPercent
        final Percent putPercent = new Percent(123.123D, PercentScale.RATE);

        //putPercentVector
        final Percent[] putPercentVector = new Percent[3];
        for (int i = 0; i < putPercentVector.length; i++) {
            putPercentVector[i] = new Percent(new Decimal((i + 1) + "." + (i + 1)), PercentScale.RATE);
        }

        //putPercentVectorAsList
        final List<Percent> putPercentVectorAsList = Arrays.asList(putPercentVector);

        //putPercentMatrix
        final Percent[][] putPercentMatrix = new Percent[4][];
        for (int row = 0; row < putPercentMatrix.length; row++) {
            final Percent[] vector = new Percent[3];
            for (int col = 0; col < vector.length; col++) {
                vector[col] = new Percent(new Decimal((row + 1) + "." + (col + 1)), PercentScale.RATE);
            }
            putPercentMatrix[row] = vector;
        }

        //putPercentMatrixAsList
        final List<List<Percent>> putPercentMatrixAsList = new ArrayList<List<Percent>>(putPercentMatrix.length);
        for (final Percent[] vector : putPercentMatrix) {
            putPercentMatrixAsList.add(Arrays.asList(vector));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putDecimal("putPercent", putPercent);

                inputs.putDecimalVector("putPercentVector", putPercentVector);

                inputs.putDecimalVectorAsList("putPercentVectorAsList", putPercentVectorAsList);

                inputs.putDecimalMatrix("putPercentMatrix", putPercentMatrix);

                inputs.putDecimalMatrixAsList("putPercentMatrixAsList", putPercentMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestPercent.class.getSimpleName() + ".py",
                        InputsAndResultsTestPercent.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getPercent
                final Percent getPercent = results.getDecimal("getPercent", Percent.ZERO_PERCENT);
                Assertions.assertThat(putPercent).isEqualTo(getPercent);

                //getPercentVector
                final Percent[] getPercentVector = results.getDecimalVector("getPercentVector", Percent.ZERO_PERCENT);
                Assertions.assertThat(putPercentVector).isEqualTo(getPercentVector);

                //getPercentVectorAsList
                final List<Percent> getPercentVectorAsList = results.getDecimalVectorAsList("getPercentVectorAsList",
                        Percent.ZERO_PERCENT);
                Assertions.assertThat(putPercentVectorAsList).isEqualTo(getPercentVectorAsList);

                //getPercentMatrix
                final Percent[][] getPercentMatrix = results.getDecimalMatrix("getPercentMatrix", Percent.ZERO_PERCENT);
                Assertions.assertThat(putPercentMatrix).isEqualTo(getPercentMatrix);

                //getPercentMatrixAsList
                final List<List<Percent>> getPercentMatrixAsList = results
                        .getDecimalMatrixAsList("getPercentMatrixAsList", Percent.ZERO_PERCENT);
                Assertions.assertThat(putPercentMatrixAsList).isEqualTo(getPercentMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
