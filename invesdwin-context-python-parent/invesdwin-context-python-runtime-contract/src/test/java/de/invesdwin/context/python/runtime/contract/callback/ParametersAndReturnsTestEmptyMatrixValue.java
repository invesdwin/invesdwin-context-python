package de.invesdwin.context.python.runtime.contract.callback;

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
public class ParametersAndReturnsTestEmptyMatrixValue {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestEmptyMatrixValue(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testEmptyMatrixValue() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestEmptyMatrixValueCallback callback = new ParametersAndReturnsTestEmptyMatrixValueCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(
                        new ClassPathResource(ParametersAndReturnsTestEmptyMatrixValue.class.getSimpleName() + ".py",
                                ParametersAndReturnsTestEmptyMatrixValue.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestEmptyMatrixValueCallback {

        private final boolean[][] putBooleanMatrix;
        private final List<List<Boolean>> putBooleanMatrixAsList;

        private final byte[][] putByteMatrix;
        private final List<List<Byte>> putByteMatrixAsList;

        private final char[][] putCharacterMatrix;
        private final List<List<Character>> putCharacterMatrixAsList;

        private final Decimal[][] putDecimalMatrix;
        private final List<List<Decimal>> putDecimalMatrixAsList;

        private final double[][] putDoubleMatrix;
        private final List<List<Double>> putDoubleMatrixAsList;

        private final float[][] putFloatMatrix;
        private final List<List<Float>> putFloatMatrixAsList;

        private final int[][] putIntegerMatrix;
        private final List<List<Integer>> putIntegerMatrixAsList;

        private final long[][] putLongMatrix;
        private final List<List<Long>> putLongMatrixAsList;

        private final Percent[][] putPercentMatrix;
        private final List<List<Percent>> putPercentMatrixAsList;

        private final short[][] putShortMatrix;
        private final List<List<Short>> putShortMatrixAsList;

        private final String[][] putStringMatrix;
        private final List<List<String>> putStringMatrixAsList;

        //CHECKSTYLE:OFF
        public ParametersAndReturnsTestEmptyMatrixValueCallback() {
            //CHECKSTYLE:ON
            this.putBooleanMatrix = new boolean[2][];
            for (int i = 0; i < putBooleanMatrix.length; i++) {
                putBooleanMatrix[i] = new boolean[0];
            }
            this.putBooleanMatrixAsList = Booleans.asListMatrix(putBooleanMatrix);

            this.putByteMatrix = new byte[2][];
            for (int i = 0; i < putByteMatrix.length; i++) {
                putByteMatrix[i] = new byte[0];
            }
            this.putByteMatrixAsList = Bytes.asListMatrix(putByteMatrix);

            this.putCharacterMatrix = new char[2][];
            for (int i = 0; i < putCharacterMatrix.length; i++) {
                putCharacterMatrix[i] = new char[0];
            }
            this.putCharacterMatrixAsList = Characters.asListMatrix(putCharacterMatrix);

            this.putDecimalMatrix = new Decimal[2][];
            for (int i = 0; i < putDecimalMatrix.length; i++) {
                putDecimalMatrix[i] = new Decimal[0];
            }
            this.putDecimalMatrixAsList = Decimal.asListMatrix(putDecimalMatrix);

            this.putDoubleMatrix = new double[2][];
            for (int i = 0; i < putDoubleMatrix.length; i++) {
                putDoubleMatrix[i] = new double[0];
            }
            this.putDoubleMatrixAsList = Doubles.asListMatrix(putDoubleMatrix);

            this.putFloatMatrix = new float[2][];
            for (int i = 0; i < putFloatMatrix.length; i++) {
                putFloatMatrix[i] = new float[0];
            }
            this.putFloatMatrixAsList = Floats.asListMatrix(putFloatMatrix);

            this.putIntegerMatrix = new int[2][];
            for (int i = 0; i < putIntegerMatrix.length; i++) {
                putIntegerMatrix[i] = new int[0];
            }
            this.putIntegerMatrixAsList = Integers.asListMatrix(putIntegerMatrix);

            this.putLongMatrix = new long[2][];
            for (int i = 0; i < putLongMatrix.length; i++) {
                putLongMatrix[i] = new long[0];
            }
            this.putLongMatrixAsList = Longs.asListMatrix(putLongMatrix);

            this.putPercentMatrix = new Percent[2][];
            for (int i = 0; i < putPercentMatrix.length; i++) {
                putPercentMatrix[i] = new Percent[0];
            }
            this.putPercentMatrixAsList = Percent.asListMatrix(putPercentMatrix);

            this.putShortMatrix = new short[2][];
            for (int i = 0; i < putShortMatrix.length; i++) {
                putShortMatrix[i] = new short[0];
            }
            this.putShortMatrixAsList = Shorts.asListMatrix(putShortMatrix);

            this.putStringMatrix = new String[2][];
            for (int i = 0; i < putStringMatrix.length; i++) {
                putStringMatrix[i] = new String[0];
            }
            this.putStringMatrixAsList = Strings.asListMatrix(putStringMatrix);
        }

        public boolean[][] getBooleanMatrix() {
            return putBooleanMatrix;
        }

        public void setBooleanMatrix(final boolean[][] putBooleanMatrix) {
            Assertions.checkEquals(this.putBooleanMatrix, putBooleanMatrix);
        }

        public List<List<Boolean>> getBooleanMatrixAsList() {
            return putBooleanMatrixAsList;
        }

        public void setBooleanMatrixAsList(final List<List<Boolean>> putBooleanMatrixAsList) {
            Assertions.checkEquals(this.putBooleanMatrixAsList, putBooleanMatrixAsList);
        }

        public byte[][] getByteMatrix() {
            return putByteMatrix;
        }

        public void setByteMatrix(final byte[][] putByteMatrix) {
            Assertions.checkEquals(this.putByteMatrix, putByteMatrix);
        }

        public List<List<Byte>> getByteMatrixAsList() {
            return putByteMatrixAsList;
        }

        public void setByteMatrixAsList(final List<List<Byte>> putByteMatrixAsList) {
            Assertions.checkEquals(this.putByteMatrixAsList, putByteMatrixAsList);
        }

        public char[][] getCharacterMatrix() {
            return putCharacterMatrix;
        }

        public void setCharacterMatrix(final char[][] putCharacterMatrix) {
            Assertions.checkEquals(this.putCharacterMatrix, putCharacterMatrix);
        }

        public List<List<Character>> getCharacterMatrixAsList() {
            return putCharacterMatrixAsList;
        }

        public void setCharacterMatrixAsList(final List<List<Character>> putCharacterMatrixAsList) {
            Assertions.checkEquals(this.putCharacterMatrixAsList, putCharacterMatrixAsList);
        }

        public Decimal[][] getDecimalMatrix() {
            return putDecimalMatrix;
        }

        public void setDecimalMatrix(final Decimal[][] putDecimalMatrix) {
            Assertions.checkEquals(this.putDecimalMatrix, putDecimalMatrix);
        }

        public List<List<Decimal>> getDecimalMatrixAsList() {
            return putDecimalMatrixAsList;
        }

        public void setDecimalMatrixAsList(final List<List<Decimal>> putDecimalMatrixAsList) {
            Assertions.checkEquals(this.putDecimalMatrixAsList, putDecimalMatrixAsList);
        }

        public double[][] getDoubleMatrix() {
            return putDoubleMatrix;
        }

        public void setDoubleMatrix(final double[][] putDoubleMatrix) {
            Assertions.checkEquals(this.putDoubleMatrix, putDoubleMatrix);
        }

        public List<List<Double>> getDoubleMatrixAsList() {
            return putDoubleMatrixAsList;
        }

        public void setDoubleMatrixAsList(final List<List<Double>> putDoubleMatrixAsList) {
            Assertions.checkEquals(this.putDoubleMatrixAsList, putDoubleMatrixAsList);
        }

        public float[][] getFloatMatrix() {
            return putFloatMatrix;
        }

        public void setFloatMatrix(final float[][] putFloatMatrix) {
            Assertions.checkEquals(this.putFloatMatrix, putFloatMatrix);
        }

        public List<List<Float>> getFloatMatrixAsList() {
            return putFloatMatrixAsList;
        }

        public void setFloatMatrixAsList(final List<List<Float>> putFloatMatrixAsList) {
            Assertions.checkEquals(this.putFloatMatrixAsList, putFloatMatrixAsList);
        }

        public int[][] getIntegerMatrix() {
            return putIntegerMatrix;
        }

        public void setIntegerMatrix(final int[][] putIntegerMatrix) {
            Assertions.checkEquals(this.putIntegerMatrix, putIntegerMatrix);
        }

        public List<List<Integer>> getIntegerMatrixAsList() {
            return putIntegerMatrixAsList;
        }

        public void setIntegerMatrixAsList(final List<List<Integer>> putIntegerMatrixAsList) {
            Assertions.checkEquals(this.putIntegerMatrixAsList, putIntegerMatrixAsList);
        }

        public long[][] getLongMatrix() {
            return putLongMatrix;
        }

        public void setLongMatrix(final long[][] putLongMatrix) {
            Assertions.checkEquals(this.putLongMatrix, putLongMatrix);
        }

        public List<List<Long>> getLongMatrixAsList() {
            return putLongMatrixAsList;
        }

        public void setLongMatrixAsList(final List<List<Long>> putLongMatrixAsList) {
            Assertions.checkEquals(this.putLongMatrixAsList, putLongMatrixAsList);
        }

        public Percent[][] getPercentMatrix() {
            return putPercentMatrix;
        }

        public void setPercentMatrix(final Percent[][] putPercentMatrix) {
            Assertions.checkEquals(this.putPercentMatrix, putPercentMatrix);
        }

        public List<List<Percent>> getPercentMatrixAsList() {
            return putPercentMatrixAsList;
        }

        public void setPercentMatrixAsList(final List<List<Percent>> putPercentMatrixAsList) {
            Assertions.checkEquals(this.putPercentMatrixAsList, putPercentMatrixAsList);
        }

        public short[][] getShortMatrix() {
            return putShortMatrix;
        }

        public void setShortMatrix(final short[][] putShortMatrix) {
            Assertions.checkEquals(this.putShortMatrix, putShortMatrix);
        }

        public List<List<Short>> getShortMatrixAsList() {
            return putShortMatrixAsList;
        }

        public void setShortMatrixAsList(final List<List<Short>> putShortMatrixAsList) {
            Assertions.checkEquals(this.putShortMatrixAsList, putShortMatrixAsList);
        }

        public String[][] getStringMatrix() {
            return putStringMatrix;
        }

        public void setStringMatrix(final String[][] putStringMatrix) {
            Assertions.checkEquals(this.putStringMatrix, putStringMatrix);
        }

        public List<List<String>> getStringMatrixAsList() {
            return putStringMatrixAsList;
        }

        public void setStringMatrixAsList(final List<List<String>> putStringMatrixAsList) {
            Assertions.checkEquals(this.putStringMatrixAsList, putStringMatrixAsList);
        }

    }

}
