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
public class InputsAndResultsTestByte {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestByte(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testByte() {
        //putByte
        final byte putByte = 123;

        //putByteVector
        final byte[] putByteVector = new byte[3];
        for (byte i = 0; i < putByteVector.length; i++) {
            putByteVector[i] = Byte.parseByte((i + 1) + "" + (i + 1));
        }

        //putByteVectorAsList
        final List<Byte> putByteVectorAsList = Arrays.asList(Arrays.toObject(putByteVector));

        //putByteMatrix
        final byte[][] putByteMatrix = new byte[4][];
        for (byte row = 0; row < putByteMatrix.length; row++) {
            final byte[] vector = new byte[3];
            for (byte col = 0; col < vector.length; col++) {
                vector[col] = Byte.parseByte((row + 1) + "" + (col + 1));
            }
            putByteMatrix[row] = vector;
        }

        //putByteMatrixAsList
        final List<List<Byte>> putByteMatrixAsList = new ArrayList<List<Byte>>(putByteMatrix.length);
        for (final byte[] vector : putByteMatrix) {
            putByteMatrixAsList.add(Arrays.asList(Arrays.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putByte("putByte", putByte);

                inputs.putByteVector("putByteVector", putByteVector);

                inputs.putByteVectorAsList("putByteVectorAsList", putByteVectorAsList);

                inputs.putByteMatrix("putByteMatrix", putByteMatrix);

                inputs.putByteMatrixAsList("putByteMatrixAsList", putByteMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestByte.class.getSimpleName() + ".py",
                        InputsAndResultsTestByte.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getByte
                final Byte getByte = results.getByte("getByte");
                Assertions.assertThat(putByte).isEqualTo(getByte);

                //getByteVector
                final byte[] getByteVector = results.getByteVector("getByteVector");
                Assertions.assertThat(putByteVector).isEqualTo(getByteVector);

                //getByteVectorAsList
                final List<Byte> getByteVectorAsList = results.getByteVectorAsList("getByteVectorAsList");
                Assertions.assertThat(putByteVectorAsList).isEqualTo(getByteVectorAsList);

                //getByteMatrix
                final byte[][] getByteMatrix = results.getByteMatrix("getByteMatrix");
                Assertions.assertThat(putByteMatrix).isEqualTo(getByteMatrix);

                //getByteMatrixAsList
                final List<List<Byte>> getByteMatrixAsList = results.getByteMatrixAsList("getByteMatrixAsList");
                Assertions.assertThat(putByteMatrixAsList).isEqualTo(getByteMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
