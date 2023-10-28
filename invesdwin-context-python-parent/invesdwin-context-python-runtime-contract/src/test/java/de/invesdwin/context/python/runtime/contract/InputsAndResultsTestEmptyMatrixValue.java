package de.invesdwin.context.python.runtime.contract;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.lang.string.Strings;
import de.invesdwin.util.math.Booleans;
import de.invesdwin.util.math.Bytes;
import de.invesdwin.util.math.Characters;
import de.invesdwin.util.math.Doubles;
import de.invesdwin.util.math.Floats;
import de.invesdwin.util.math.Integers;
import de.invesdwin.util.math.Longs;
import de.invesdwin.util.math.Shorts;
import de.invesdwin.util.math.decimal.Decimal;
import de.invesdwin.util.math.decimal.scaled.Percent;

@NotThreadSafe
public class InputsAndResultsTestEmptyMatrixValue {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestEmptyMatrixValue(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testEmptyMatrixValue() {
        final boolean[][] putBooleanMatrix = new boolean[2][];
        for (int i = 0; i < putBooleanMatrix.length; i++) {
            putBooleanMatrix[i] = new boolean[0];
        }
        final List<List<Boolean>> putBooleanMatrixAsList = Booleans.asListMatrix(putBooleanMatrix);

        final byte[][] putByteMatrix = new byte[2][];
        for (int i = 0; i < putByteMatrix.length; i++) {
            putByteMatrix[i] = new byte[0];
        }
        final List<List<Byte>> putByteMatrixAsList = Bytes.asListMatrix(putByteMatrix);

        final char[][] putCharacterMatrix = new char[2][];
        for (int i = 0; i < putCharacterMatrix.length; i++) {
            putCharacterMatrix[i] = new char[0];
        }
        final List<List<Character>> putCharacterMatrixAsList = Characters.asListMatrix(putCharacterMatrix);

        final Decimal[][] putDecimalMatrix = new Decimal[2][];
        for (int i = 0; i < putDecimalMatrix.length; i++) {
            putDecimalMatrix[i] = new Decimal[0];
        }
        final List<List<Decimal>> putDecimalMatrixAsList = Decimal.asListMatrix(putDecimalMatrix);

        final double[][] putDoubleMatrix = new double[2][];
        for (int i = 0; i < putDoubleMatrix.length; i++) {
            putDoubleMatrix[i] = new double[0];
        }
        final List<List<Double>> putDoubleMatrixAsList = Doubles.asListMatrix(putDoubleMatrix);

        final float[][] putFloatMatrix = new float[2][];
        for (int i = 0; i < putFloatMatrix.length; i++) {
            putFloatMatrix[i] = new float[0];
        }
        final List<List<Float>> putFloatMatrixAsList = Floats.asListMatrix(putFloatMatrix);

        final int[][] putIntegerMatrix = new int[2][];
        for (int i = 0; i < putIntegerMatrix.length; i++) {
            putIntegerMatrix[i] = new int[0];
        }
        final List<List<Integer>> putIntegerMatrixAsList = Integers.asListMatrix(putIntegerMatrix);

        final long[][] putLongMatrix = new long[2][];
        for (int i = 0; i < putLongMatrix.length; i++) {
            putLongMatrix[i] = new long[0];
        }
        final List<List<Long>> putLongMatrixAsList = Longs.asListMatrix(putLongMatrix);

        final Percent[][] putPercentMatrix = new Percent[2][];
        for (int i = 0; i < putPercentMatrix.length; i++) {
            putPercentMatrix[i] = new Percent[0];
        }
        final List<List<Percent>> putPercentMatrixAsList = Percent.asListMatrix(putPercentMatrix);

        final short[][] putShortMatrix = new short[2][];
        for (int i = 0; i < putShortMatrix.length; i++) {
            putShortMatrix[i] = new short[0];
        }
        final List<List<Short>> putShortMatrixAsList = Shorts.asListMatrix(putShortMatrix);

        final String[][] putStringMatrix = new String[2][];
        for (int i = 0; i < putStringMatrix.length; i++) {
            putStringMatrix[i] = new String[0];
        }
        final List<List<String>> putStringMatrixAsList = Strings.asListMatrix(putStringMatrix);

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putBooleanMatrix("putBooleanMatrix", putBooleanMatrix);
                inputs.putBooleanMatrixAsList("putBooleanMatrixAsList", putBooleanMatrixAsList);

                inputs.putByteMatrix("putByteMatrix", putByteMatrix);
                inputs.putByteMatrixAsList("putByteMatrixAsList", putByteMatrixAsList);

                inputs.putCharacterMatrix("putCharacterMatrix", putCharacterMatrix);
                inputs.putCharacterMatrixAsList("putCharacterMatrixAsList", putCharacterMatrixAsList);

                inputs.putDecimalMatrix("putDecimalMatrix", putDecimalMatrix);
                inputs.putDecimalMatrixAsList("putDecimalMatrixAsList", putDecimalMatrixAsList);

                inputs.putDoubleMatrix("putDoubleMatrix", putDoubleMatrix);
                inputs.putDoubleMatrixAsList("putDoubleMatrixAsList", putDoubleMatrixAsList);

                inputs.putFloatMatrix("putFloatMatrix", putFloatMatrix);
                inputs.putFloatMatrixAsList("putFloatMatrixAsList", putFloatMatrixAsList);

                inputs.putIntegerMatrix("putIntegerMatrix", putIntegerMatrix);
                inputs.putIntegerMatrixAsList("putIntegerMatrixAsList", putIntegerMatrixAsList);

                inputs.putLongMatrix("putLongMatrix", putLongMatrix);
                inputs.putLongMatrixAsList("putLongMatrixAsList", putLongMatrixAsList);

                inputs.putDecimalMatrix("putPercentMatrix", putPercentMatrix);
                inputs.putDecimalMatrixAsList("putPercentMatrixAsList", putPercentMatrixAsList);

                inputs.putShortMatrix("putShortMatrix", putShortMatrix);
                inputs.putShortMatrixAsList("putShortMatrixAsList", putShortMatrixAsList);

                inputs.putStringMatrix("putStringMatrix", putStringMatrix);
                inputs.putStringMatrixAsList("putStringMatrixAsList", putStringMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestEmptyMatrixValue.class.getSimpleName() + ".py",
                        InputsAndResultsTestEmptyMatrixValue.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                Assertions.checkEquals(putBooleanMatrix, results.getBooleanMatrix("getBooleanMatrix"));
                Assertions.checkEquals(putBooleanMatrixAsList,
                        results.getBooleanMatrixAsList("getBooleanMatrixAsList"));

                Assertions.checkEquals(putByteMatrix, results.getByteMatrix("getByteMatrix"));
                Assertions.checkEquals(putByteMatrixAsList, results.getByteMatrixAsList("getByteMatrixAsList"));

                Assertions.checkEquals(putCharacterMatrix, results.getCharacterMatrix("getCharacterMatrix"));
                Assertions.checkEquals(putCharacterMatrixAsList,
                        results.getCharacterMatrixAsList("getCharacterMatrixAsList"));

                Assertions.checkEquals(putDecimalMatrix, results.getDecimalMatrix("getDecimalMatrix"));
                Assertions.checkEquals(putDecimalMatrixAsList,
                        results.getDecimalMatrixAsList("getDecimalMatrixAsList"));

                Assertions.checkEquals(putDoubleMatrix, results.getDoubleMatrix("getDoubleMatrix"));
                Assertions.checkEquals(putDoubleMatrixAsList, results.getDoubleMatrixAsList("getDoubleMatrixAsList"));

                Assertions.checkEquals(putFloatMatrix, results.getFloatMatrix("getFloatMatrix"));
                Assertions.checkEquals(putFloatMatrixAsList, results.getFloatMatrixAsList("getFloatMatrixAsList"));

                Assertions.checkEquals(putIntegerMatrix, results.getIntegerMatrix("getIntegerMatrix"));
                Assertions.checkEquals(putIntegerMatrixAsList,
                        results.getIntegerMatrixAsList("getIntegerMatrixAsList"));

                Assertions.checkEquals(putLongMatrix, results.getLongMatrix("getLongMatrix"));
                Assertions.checkEquals(putLongMatrixAsList, results.getLongMatrixAsList("getLongMatrixAsList"));

                Assertions.checkEquals(putPercentMatrix,
                        results.getDecimalMatrix("getPercentMatrix", Percent.ZERO_PERCENT));
                Assertions.checkEquals(putPercentMatrixAsList,
                        results.getDecimalMatrixAsList("getPercentMatrixAsList", Percent.ZERO_PERCENT));

                Assertions.checkEquals(putShortMatrix, results.getShortMatrix("getShortMatrix"));
                Assertions.checkEquals(putShortMatrixAsList, results.getShortMatrixAsList("getShortMatrixAsList"));

                Assertions.checkEquals(putStringMatrix, results.getStringMatrix("getStringMatrix"));
                Assertions.checkEquals(putStringMatrixAsList, results.getStringMatrixAsList("getStringMatrixAsList"));
                return null;
            }
        }.run(runner);
    }

}
