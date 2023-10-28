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
import de.invesdwin.util.math.decimal.Decimal;
import de.invesdwin.util.math.decimal.scaled.Percent;

@NotThreadSafe
public class ParametersAndReturnsTestNullPutGet {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestNullPutGet(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testNullPutGet() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestNullPutGetCallback callback = new ParametersAndReturnsTestNullPutGetCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(ParametersAndReturnsTestNullPutGet.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestNullPutGet.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestNullPutGetCallback {

        private final boolean[] putBooleanVector = null;
        private final List<Boolean> putBooleanVectorAsList = null;
        private final boolean[][] putBooleanMatrix = null;
        private final List<List<Boolean>> putBooleanMatrixAsList = null;

        private final byte[] putByteVector = null;
        private final List<Byte> putByteVectorAsList = null;
        private final byte[][] putByteMatrix = null;
        private final List<List<Byte>> putByteMatrixAsList = null;

        private final char[] putCharacterVector = null;
        private final char[][] putCharacterVectorAsList = null;
        private final List<Character> putCharacterMatrix = null;
        private final List<List<Character>> putCharacterMatrixAsList = null;

        private final Decimal[] putDecimalVector = null;
        private final List<Decimal> putDecimalVectorAsList = null;
        private final Decimal[][] putDecimalMatrix = null;
        private final List<List<Decimal>> putDecimalMatrixAsList = null;

        private final double[] putDoubleVector = null;
        private final List<Double> putDoubleVectorAsList = null;
        private final double[][] putDoubleMatrix = null;
        private final List<List<Double>> putDoubleMatrixAsList = null;

        private final float[] putFloatVector = null;
        private final List<Float> putFloatVectorAsList = null;
        private final float[][] putFloatMatrix = null;
        private final List<List<Float>> putFloatMatrixAsList = null;

        private final int[] putIntegerVector = null;
        private final List<Integer> putIntegerVectorAsList = null;
        private final int[][] putIntegerMatrix = null;
        private final List<List<Integer>> putIntegerMatrixAsList = null;

        private final long[] putLongVector = null;
        private final List<Long> putLongVectorAsList = null;
        private final long[][] putLongMatrix = null;
        private final List<List<Long>> putLongMatrixAsList = null;

        private final Percent[] putPercentVector = null;
        private final List<Percent> putPercentVectorAsList = null;
        private final Percent[][] putPercentMatrix = null;
        private final List<List<Percent>> putPercentMatrixAsList = null;

        private final short[] putShortVector = null;
        private final List<Short> putShortVectorAsList = null;
        private final short[][] putShortMatrix = null;
        private final List<List<Short>> putShortMatrixAsList = null;

        private final String[] putStringVector = null;
        private final List<String> putStringVectorAsList = null;
        private final String[][] putStringMatrix = null;
        private final List<List<String>> putStringMatrixAsList = null;

        public ParametersAndReturnsTestNullPutGetCallback() {}

        public boolean[] getBooleanVector() {
            return putBooleanVector;
        }

        public void setBooleanVector(final boolean[] putBooleanVector) {
            Assertions.checkEquals(this.putBooleanVector, putBooleanVector);
        }

        public List<Boolean> getBooleanVectorAsList() {
            return putBooleanVectorAsList;
        }

        public void setBooleanVectorAsList(final List<Boolean> putBooleanVectorAsList) {
            Assertions.checkEquals(this.putBooleanVectorAsList, putBooleanVectorAsList);
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

        public byte[] getByteVector() {
            return putByteVector;
        }

        public void setByteVector(final byte[] putByteVector) {
            Assertions.checkEquals(this.putByteVector, putByteVector);
        }

        public List<Byte> getByteVectorAsList() {
            return putByteVectorAsList;
        }

        public void setByteVectorAsList(final List<Byte> putByteVectorAsList) {
            Assertions.checkEquals(this.putByteVectorAsList, putByteVectorAsList);
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

        public char[] getCharacterVector() {
            return putCharacterVector;
        }

        public void setCharacterVector(final char[] putCharacterVector) {
            Assertions.checkEquals(this.putCharacterVector, putCharacterVector);
        }

        public char[][] getCharacterVectorAsList() {
            return putCharacterVectorAsList;
        }

        public void setCharacterVectorAsList(final char[][] putCharacterVectorAsList) {
            Assertions.checkEquals(this.putCharacterVectorAsList, putCharacterVectorAsList);
        }

        public List<Character> getCharacterMatrix() {
            return putCharacterMatrix;
        }

        public void setCharacterMatrix(final List<Character> putCharacterMatrix) {
            Assertions.checkEquals(this.putCharacterMatrix, putCharacterMatrix);
        }

        public List<List<Character>> getCharacterMatrixAsList() {
            return putCharacterMatrixAsList;
        }

        public void setCharacterMatrixAsList(final List<List<Character>> putCharacterMatrixAsList) {
            Assertions.checkEquals(this.putCharacterMatrixAsList, putCharacterMatrixAsList);
        }

        public Decimal[] getDecimalVector() {
            return putDecimalVector;
        }

        public void setDecimalVector(final Decimal[] putDecimalVector) {
            Assertions.checkEquals(this.putDecimalVector, putDecimalVector);
        }

        public List<Decimal> getDecimalVectorAsList() {
            return putDecimalVectorAsList;
        }

        public void setDecimalVectorAsList(final List<Decimal> putDecimalVectorAsList) {
            Assertions.checkEquals(this.putDecimalVectorAsList, putDecimalVectorAsList);
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

        public double[] getDoubleVector() {
            return putDoubleVector;
        }

        public void setDoubleVector(final double[] putDoubleVector) {
            Assertions.checkEquals(this.putDoubleVector, putDoubleVector);
        }

        public List<Double> getDoubleVectorAsList() {
            return putDoubleVectorAsList;
        }

        public void setDoubleVectorAsList(final List<Double> putDoubleVectorAsList) {
            Assertions.checkEquals(this.putDoubleVectorAsList, putDoubleVectorAsList);
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

        public float[] getFloatVector() {
            return putFloatVector;
        }

        public void setFloatVector(final float[] putFloatVector) {
            Assertions.checkEquals(this.putFloatVector, putFloatVector);
        }

        public List<Float> getFloatVectorAsList() {
            return putFloatVectorAsList;
        }

        public void setFloatVectorAsList(final List<Float> putFloatVectorAsList) {
            Assertions.checkEquals(this.putFloatVectorAsList, putFloatVectorAsList);
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

        public int[] getIntegerVector() {
            return putIntegerVector;
        }

        public void setIntegerVector(final int[] putIntegerVector) {
            Assertions.checkEquals(this.putIntegerVector, putIntegerVector);
        }

        public List<Integer> getIntegerVectorAsList() {
            return putIntegerVectorAsList;
        }

        public void setIntegerVectorAsList(final List<Integer> putIntegerVectorAsList) {
            Assertions.checkEquals(this.putIntegerVectorAsList, putIntegerVectorAsList);
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

        public long[] getLongVector() {
            return putLongVector;
        }

        public void setLongVector(final long[] putLongVector) {
            Assertions.checkEquals(this.putLongVector, putLongVector);
        }

        public List<Long> getLongVectorAsList() {
            return putLongVectorAsList;
        }

        public void setLongVectorAsList(final List<Long> putLongVectorAsList) {
            Assertions.checkEquals(this.putLongVectorAsList, putLongVectorAsList);
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

        public Percent[] getPercentVector() {
            return putPercentVector;
        }

        public void setPercentVector(final Percent[] putPercentVector) {
            Assertions.checkEquals(this.putPercentVector, putPercentVector);
        }

        public List<Percent> getPercentVectorAsList() {
            return putPercentVectorAsList;
        }

        public void setPercentVectorAsList(final List<Percent> putPercentVectorAsList) {
            Assertions.checkEquals(this.putPercentVectorAsList, putPercentVectorAsList);
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

        public short[] getShortVector() {
            return putShortVector;
        }

        public void setShortVector(final short[] putShortVector) {
            Assertions.checkEquals(this.putShortVector, putShortVector);
        }

        public List<Short> getShortVectorAsList() {
            return putShortVectorAsList;
        }

        public void setShortVectorAsList(final List<Short> putShortVectorAsList) {
            Assertions.checkEquals(this.putShortVectorAsList, putShortVectorAsList);
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

        public String[] getStringVector() {
            return putStringVector;
        }

        public void setStringVector(final String[] putStringVector) {
            Assertions.checkEquals(this.putStringVector, putStringVector);
        }

        public List<String> getStringVectorAsList() {
            return putStringVectorAsList;
        }

        public void setStringVectorAsList(final List<String> putStringVectorAsList) {
            Assertions.checkEquals(this.putStringVectorAsList, putStringVectorAsList);
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
