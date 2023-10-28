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
public class ParametersAndReturnsTestFloat {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestFloat(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testFloat() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestFloatCallback callback = new ParametersAndReturnsTestFloatCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(ParametersAndReturnsTestFloat.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestFloat.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestFloatCallback {

        //putFloat
        private final float putFloat;

        //putFloatVector
        private final float[] putFloatVector;

        //putFloatVectorAsList
        private final List<Float> putFloatVectorAsList;

        //putFloatMatrix
        private final float[][] putFloatMatrix;

        //putFloatMatrixAsList
        private final List<List<Float>> putFloatMatrixAsList;

        public ParametersAndReturnsTestFloatCallback() {
            //putFloat
            this.putFloat = 123.123F;

            //putFloatVector
            this.putFloatVector = new float[3];
            for (int i = 0; i < putFloatVector.length; i++) {
                putFloatVector[i] = Float.parseFloat((i + 1) + "." + (i + 1));
            }

            //putFloatVectorAsList
            this.putFloatVectorAsList = Arrays.asList(Arrays.toObject(putFloatVector));

            //putFloatMatrix
            this.putFloatMatrix = new float[4][];
            for (int row = 0; row < putFloatMatrix.length; row++) {
                final float[] vector = new float[3];
                for (int col = 0; col < vector.length; col++) {
                    vector[col] = Float.parseFloat((row + 1) + "." + (col + 1));
                }
                putFloatMatrix[row] = vector;
            }

            //putFloatMatrixAsList
            this.putFloatMatrixAsList = new ArrayList<List<Float>>(putFloatMatrix.length);
            for (final float[] vector : putFloatMatrix) {
                putFloatMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
            }
        }

        public float getFloat() {
            return putFloat;
        }

        public void setFloat(final float putFloat) {
            Assertions.assertThat(this.putFloat).isEqualTo(putFloat);
        }

        public float[] getFloatVector() {
            return putFloatVector;
        }

        public void setFloatVector(final float[] putFloatVector) {
            Assertions.assertThat(this.putFloatVector).isEqualTo(putFloatVector);
        }

        public List<Float> getFloatVectorAsList() {
            return putFloatVectorAsList;
        }

        public void setFloatVectorAsList(final List<Float> putFloatVectorAsList) {
            Assertions.assertThat(this.putFloatVectorAsList).isEqualTo(putFloatVectorAsList);
        }

        public float[][] getFloatMatrix() {
            return putFloatMatrix;
        }

        public void setFloatMatrix(final float[][] putFloatMatrix) {
            Assertions.assertThat(this.putFloatMatrix).isEqualTo(putFloatMatrix);
        }

        public List<List<Float>> getFloatMatrixAsList() {
            return putFloatMatrixAsList;
        }

        public void setFloatMatrixAsList(final List<List<Float>> putFloatMatrixAsList) {
            Assertions.assertThat(this.putFloatMatrixAsList).isEqualTo(putFloatMatrixAsList);
        }

    }

}
