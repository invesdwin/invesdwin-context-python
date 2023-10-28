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
public class ParametersAndReturnsTestShort {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestShort(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testShort() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestShortCallback callback = new ParametersAndReturnsTestShortCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(ParametersAndReturnsTestShort.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestShort.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestShortCallback {

        //putShort
        private final short putShort;

        //putShortVector
        private final short[] putShortVector;

        //putShortVectorAsList
        private final List<Short> putShortVectorAsList;

        //putShortMatrix
        private final short[][] putShortMatrix;

        //putShortMatrixAsList
        private final List<List<Short>> putShortMatrixAsList;

        public ParametersAndReturnsTestShortCallback() {
            //putShort
            this.putShort = 123;

            //putShortVector
            this.putShortVector = new short[3];
            for (int i = 0; i < putShortVector.length; i++) {
                putShortVector[i] = Short.parseShort((i + 1) + "" + (i + 1));
            }

            //putShortVectorAsList
            this.putShortVectorAsList = Arrays.asList(Arrays.toObject(putShortVector));

            //putShortMatrix
            this.putShortMatrix = new short[4][];
            for (int row = 0; row < putShortMatrix.length; row++) {
                final short[] vector = new short[3];
                for (int col = 0; col < vector.length; col++) {
                    vector[col] = Short.parseShort((row + 1) + "" + (col + 1));
                }
                putShortMatrix[row] = vector;
            }

            //putShortMatrixAsList
            this.putShortMatrixAsList = new ArrayList<List<Short>>(putShortMatrix.length);
            for (final short[] vector : putShortMatrix) {
                putShortMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
            }
        }

        public short getShort() {
            return putShort;
        }

        public void setShort(final short putShort) {
            Assertions.assertThat(this.putShort).isEqualTo(putShort);
        }

        public short[] getShortVector() {
            return putShortVector;
        }

        public void setShortVector(final short[] putShortVector) {
            Assertions.assertThat(this.putShortVector).isEqualTo(putShortVector);
        }

        public List<Short> getShortVectorAsList() {
            return putShortVectorAsList;
        }

        public void setShortVectorAsList(final List<Short> putShortVectorAsList) {
            Assertions.assertThat(this.putShortVectorAsList).isEqualTo(putShortVectorAsList);
        }

        public short[][] getShortMatrix() {
            return putShortMatrix;
        }

        public void setShortMatrix(final short[][] putShortMatrix) {
            Assertions.assertThat(this.putShortMatrix).isEqualTo(putShortMatrix);
        }

        public List<List<Short>> getShortMatrixAsList() {
            return putShortMatrixAsList;
        }

        public void setShortMatrixAsList(final List<List<Short>> putShortMatrixAsList) {
            Assertions.assertThat(this.putShortMatrixAsList).isEqualTo(putShortMatrixAsList);
        }

    }

}
