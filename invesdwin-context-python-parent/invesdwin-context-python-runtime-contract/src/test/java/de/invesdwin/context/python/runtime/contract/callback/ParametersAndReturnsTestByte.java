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
public class ParametersAndReturnsTestByte {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestByte(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testByte() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestByteCallback callback = new ParametersAndReturnsTestByteCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(ParametersAndReturnsTestByte.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestByte.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestByteCallback {

        //putByte
        private final byte putByte;

        //putByteVector
        private final byte[] putByteVector;

        //putByteVectorAsList
        private final List<Byte> putByteVectorAsList;

        //putByteMatrix
        private final byte[][] putByteMatrix;

        //putByteMatrixAsList
        private final List<List<Byte>> putByteMatrixAsList;

        public ParametersAndReturnsTestByteCallback() {
            //putByte
            this.putByte = 123;

            //putByteVector
            this.putByteVector = new byte[3];
            for (byte i = 0; i < putByteVector.length; i++) {
                putByteVector[i] = Byte.parseByte((i + 1) + "" + (i + 1));
            }

            //putByteVectorAsList
            this.putByteVectorAsList = Arrays.asList(Arrays.toObject(putByteVector));

            //putByteMatrix
            this.putByteMatrix = new byte[4][];
            for (byte row = 0; row < putByteMatrix.length; row++) {
                final byte[] vector = new byte[3];
                for (byte col = 0; col < vector.length; col++) {
                    vector[col] = Byte.parseByte((row + 1) + "" + (col + 1));
                }
                putByteMatrix[row] = vector;
            }

            //putByteMatrixAsList
            this.putByteMatrixAsList = new ArrayList<List<Byte>>(putByteMatrix.length);
            for (final byte[] vector : putByteMatrix) {
                putByteMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
            }
        }

        public byte getByte() {
            return putByte;
        }

        public void setByte(final byte putByte) {
            Assertions.assertThat(this.putByte).isEqualTo(putByte);
        }

        public byte[] getByteVector() {
            return putByteVector;
        }

        public void setByteVector(final byte[] putByteVector) {
            Assertions.assertThat(this.putByteVector).isEqualTo(putByteVector);
        }

        public List<Byte> getByteVectorAsList() {
            return putByteVectorAsList;
        }

        public void setByteVectorAsList(final List<Byte> putByteVectorAsList) {
            Assertions.assertThat(this.putByteVectorAsList).isEqualTo(putByteVectorAsList);
        }

        public byte[][] getByteMatrix() {
            return putByteMatrix;
        }

        public void setByteMatrix(final byte[][] putByteMatrix) {
            Assertions.assertThat(this.putByteMatrix).isEqualTo(putByteMatrix);
        }

        public List<List<Byte>> getByteMatrixAsList() {
            return putByteMatrixAsList;
        }

        public void setByteMatrixAsList(final List<List<Byte>> putByteMatrixAsList) {
            Assertions.assertThat(this.putByteMatrixAsList).isEqualTo(putByteMatrixAsList);
        }

    }

}
