package de.invesdwin.context.python.runtime.contract.callback;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.ReflectiveScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.collections.Arrays;
import de.invesdwin.util.math.decimal.Decimal;

@NotThreadSafe
public class ParametersAndReturnsTestDecimal {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestDecimal(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testDecimal() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestDecimalCallback callback = new ParametersAndReturnsTestDecimalCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(ParametersAndReturnsTestDecimal.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestDecimal.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestDecimalCallback {

        //putDecimal
        private final Decimal putDecimal;

        //putDecimalVector
        private final Decimal[] putDecimalVector;

        //putDecimalVectorAsList
        private final List<Decimal> putDecimalVectorAsList;

        //putDecimalMatrix
        private final Decimal[][] putDecimalMatrix;

        //putDecimalMatrixAsList
        private final List<List<Decimal>> putDecimalMatrixAsList;

        public ParametersAndReturnsTestDecimalCallback() {
            //putDecimal
            this.putDecimal = new Decimal("123.123");

            //putDecimalVector
            this.putDecimalVector = new Decimal[3];
            for (int i = 0; i < putDecimalVector.length; i++) {
                putDecimalVector[i] = Decimal.valueOf((i + 1) + "." + (i + 1));
            }

            //putDecimalVectorAsList
            this.putDecimalVectorAsList = Arrays.asList(putDecimalVector);

            //putDecimalMatrix
            this.putDecimalMatrix = new Decimal[4][];
            for (int row = 0; row < putDecimalMatrix.length; row++) {
                final Decimal[] vector = new Decimal[3];
                for (int col = 0; col < vector.length; col++) {
                    vector[col] = Decimal.valueOf((row + 1) + "." + (col + 1));
                }
                putDecimalMatrix[row] = vector;
            }

            //putDecimalMatrixAsList
            this.putDecimalMatrixAsList = new ArrayList<List<Decimal>>(putDecimalMatrix.length);
            for (final Decimal[] vector : putDecimalMatrix) {
                putDecimalMatrixAsList.add(Arrays.asList(vector));
            }
        }

        public Decimal getDecimal() {
            return putDecimal;
        }

        public void setDecimal(final Decimal putDecimal) {
            Assertions.assertThat(this.putDecimal).isEqualTo(putDecimal);
        }

        public Decimal[] getDecimalVector() {
            return putDecimalVector;
        }

        public void setDecimalVector(final Decimal[] putDecimalVector) {
            Assertions.assertThat(this.putDecimalVector).isEqualTo(putDecimalVector);
        }

        public List<Decimal> getDecimalVectorAsList() {
            return putDecimalVectorAsList;
        }

        public void setDecimalVectorAsList(final List<Decimal> putDecimalVectorAsList) {
            Assertions.assertThat(this.putDecimalVectorAsList).isEqualTo(putDecimalVectorAsList);
        }

        public Decimal[][] getDecimalMatrix() {
            return putDecimalMatrix;
        }

        public void setDecimalMatrix(final Decimal[][] putDecimalMatrix) {
            Assertions.assertThat(this.putDecimalMatrix).isEqualTo(putDecimalMatrix);
        }

        public List<List<Decimal>> getDecimalMatrixAsList() {
            return putDecimalMatrixAsList;
        }

        public void setDecimalMatrixAsList(final List<List<Decimal>> putDecimalMatrixAsList) {
            Assertions.assertThat(this.putDecimalMatrixAsList).isEqualTo(putDecimalMatrixAsList);
        }

    }

}
