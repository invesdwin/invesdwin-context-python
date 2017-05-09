package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.rcaller.rstuff.RCaller;

import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class Py4jScriptTaskInputsPython implements IScriptTaskInputs {

    private RCaller rcaller;

    public Py4jScriptTaskInputsPython(final RCaller rcaller) {
        this.rcaller = rcaller;
    }

    @Override
    public RCaller getEngine() {
        return rcaller;
    }

    @Override
    public void close() {
        rcaller = null;
    }

    @Override
    public void putString(final String variable, final String value) {
        if (value == null) {
            putExpression(variable, "NA_character_");
        } else {
            rcaller.getRCode().addString(variable, value);
        }
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        rcaller.getRCode().addStringArray(variable, replaceNullWithNa(value));
        rcaller.getRCode().addRCode(variable + "[ " + variable + " == \"NA_character_\" ] <- NA_character_");
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        final int rows = value.length;
        final int cols = value[0].length;
        final String[] flatMatrix = new String[rows * cols];
        int i = 0;
        for (int row = 0; row < rows; row++) {
            Assertions.checkEquals(value[row].length, cols);
            for (int col = 0; col < cols; col++) {
                flatMatrix[i] = value[row][col];
                i++;
            }
        }
        putStringVector(variable, flatMatrix);
        putExpression(variable, "matrix(" + variable + ", " + rows + ", " + cols + ", TRUE)");
    }

    private String[] replaceNullWithNa(final String[] value) {
        final String[] array = value.clone();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = "NA_character_";
            }
        }
        return array;
    }

    @Override
    public void putDouble(final String variable, final double value) {
        rcaller.getRCode().addDouble(variable, value);
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        rcaller.getRCode().addDoubleArray(variable, value);
    }

    @Override
    public void putDoubleMatrix(final String variable, final double[][] value) {
        rcaller.getRCode().addDoubleMatrix(variable, value);
    }

    @Override
    public void putInteger(final String variable, final int value) {
        rcaller.getRCode().addInt(variable, value);
        putExpression(variable, "as.integer(" + variable + ")");
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        rcaller.getRCode().addIntArray(variable, value);
        putExpression(variable, "as.integer(" + variable + ")");
    }

    @Override
    public void putIntegerMatrix(final String variable, final int[][] value) {
        final double[][] matrix = new double[value.length][];
        for (int i = 0; i < matrix.length; i++) {
            final int[] intVector = value[i];
            final double[] vector = new double[intVector.length];
            for (int j = 0; j < vector.length; j++) {
                vector[j] = intVector[j];
            }
            matrix[i] = vector;
        }
        rcaller.getRCode().addDoubleMatrix(variable, matrix);
        putExpression(variable, "array(as.integer(" + variable + "), dim(" + variable + "))");
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        rcaller.getRCode().addLogical(variable, value);
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        rcaller.getRCode().addLogicalArray(variable, value);
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        final double[][] matrix = new double[value.length][];
        for (int i = 0; i < matrix.length; i++) {
            final boolean[] boolVector = value[i];
            final double[] vector = new double[boolVector.length];
            for (int j = 0; j < vector.length; j++) {
                final boolean bool = boolVector[j];
                if (bool) {
                    vector[j] = 1D;
                } else {
                    vector[j] = 0D;
                }
            }
            matrix[i] = vector;
        }
        rcaller.getRCode().addDoubleMatrix(variable, matrix);
        putExpression(variable, "array(as.logical(" + variable + "), dim(" + variable + "))");
    }

    @Override
    public void putExpression(final String variable, final String expression) {
        rcaller.getRCode().addRCode(variable + " <- " + expression);
    }

}
