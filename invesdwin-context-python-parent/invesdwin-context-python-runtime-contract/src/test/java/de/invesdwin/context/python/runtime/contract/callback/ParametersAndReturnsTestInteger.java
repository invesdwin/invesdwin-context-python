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

@NotThreadSafe
public class ParametersAndReturnsTestInteger {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestInteger(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testInteger() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestIntegerCallback callback = new ParametersAndReturnsTestIntegerCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(ParametersAndReturnsTestInteger.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestInteger.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestIntegerCallback {

        //putInteger
        private final int putInteger;

        //putIntegerVector
        private final int[] putIntegerVector;

        //putIntegerVectorAsList
        private final List<Integer> putIntegerVectorAsList;

        //putIntegerMatrix
        private final int[][] putIntegerMatrix;

        //putIntegerMatrixAsList
        private final List<List<Integer>> putIntegerMatrixAsList;

        public ParametersAndReturnsTestIntegerCallback() {
            //putInteger
            this.putInteger = 123;

            //putIntegerVector
            this.putIntegerVector = new int[3];
            for (int i = 0; i < putIntegerVector.length; i++) {
                putIntegerVector[i] = Integer.parseInt((i + 1) + "" + (i + 1));
            }

            //putIntegerVectorAsList
            this.putIntegerVectorAsList = Arrays.asList(Arrays.toObject(putIntegerVector));

            //putIntegerMatrix
            this.putIntegerMatrix = new int[4][];
            for (int row = 0; row < putIntegerMatrix.length; row++) {
                final int[] vector = new int[3];
                for (int col = 0; col < vector.length; col++) {
                    vector[col] = Integer.parseInt((row + 1) + "" + (col + 1));
                }
                putIntegerMatrix[row] = vector;
            }

            //putIntegerMatrixAsList
            this.putIntegerMatrixAsList = new ArrayList<List<Integer>>(putIntegerMatrix.length);
            for (final int[] vector : putIntegerMatrix) {
                putIntegerMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
            }

        }

        public int getInteger() {
            return putInteger;
        }

        public void setInteger(final int putInteger) {
            Assertions.assertThat(this.putInteger).isEqualTo(putInteger);
        }

        public int[] getIntegerVector() {
            return putIntegerVector;
        }

        public void setIntegerVector(final int[] putIntegerVector) {
            Assertions.assertThat(this.putIntegerVector).isEqualTo(putIntegerVector);
        }

        public List<Integer> getIntegerVectorAsList() {
            return putIntegerVectorAsList;
        }

        public void setIntegerVectorAsList(final List<Integer> putIntegerVectorAsList) {
            Assertions.assertThat(this.putIntegerVectorAsList).isEqualTo(putIntegerVectorAsList);
        }

        public int[][] getIntegerMatrix() {
            return putIntegerMatrix;
        }

        public void setIntegerMatrix(final int[][] putIntegerMatrix) {
            Assertions.assertThat(this.putIntegerMatrix).isEqualTo(putIntegerMatrix);
        }

        public List<List<Integer>> getIntegerMatrixAsList() {
            return putIntegerMatrixAsList;
        }

        public void setIntegerMatrixAsList(final List<List<Integer>> putIntegerMatrixAsList) {
            Assertions.assertThat(this.putIntegerMatrixAsList).isEqualTo(putIntegerMatrixAsList);
        }

    }

}
