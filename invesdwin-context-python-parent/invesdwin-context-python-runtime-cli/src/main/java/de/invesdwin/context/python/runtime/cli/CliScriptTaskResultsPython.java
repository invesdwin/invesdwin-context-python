package de.invesdwin.context.python.runtime.cli;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.rcaller.rstuff.RCaller;

import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class CliScriptTaskResultsPython implements IScriptTaskResults {
    private RCaller rcaller;

    public CliScriptTaskResultsPython(final RCaller rcaller) {
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

    private void requestVariable(final String variable) {
        rcaller.runAndReturnResultOnline(variable);
    }

    @Override
    public String getString(final String variable) {
        requestVariable(variable);
        final String[] array = replaceNaWithNull(rcaller.getParser().getAsStringArray(variable));
        Assertions.checkEquals(array.length, 1);
        return array[0];
    }

    private String[] replaceNaWithNull(final String[] array) {
        for (int i = 0; i < array.length; i++) {
            if ("NA".equals(array[i])) {
                array[i] = null;
            }
        }
        return array;
    }

    @Override
    public String[] getStringVector(final String variable) {
        requestVariable(variable);
        return replaceNaWithNull(rcaller.getParser().getAsStringArray(variable));
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        final String[] ct = getStringVector(variable);
        if (ct == null) {
            return null;
        }
        rcaller.getRCode().addRCode(CliScriptTaskRunnerPython.INTERNAL_RESULT_VARIABLE + " <- dim(" + variable + ")");
        final int[] ds = getIntegerVector(CliScriptTaskRunnerPython.INTERNAL_RESULT_VARIABLE);
        if ((ds == null) || (ds.length != 2)) {
            return null;
        }
        final int m = ds[0];
        final int n = ds[1];
        final String[][] r = new String[m][n];

        int i = 0;
        int k = 0;
        while (i < n) {
            int j = 0;
            while (j < m) {
                r[(j++)][i] = ct[(k++)];
            }
            i++;
        }
        return r;
    }

    @Override
    public double getDouble(final String variable) {
        requestVariable(variable);
        final double[] array = rcaller.getParser().getAsDoubleArray(variable);
        Assertions.checkEquals(array.length, 1);
        return array[0];
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        requestVariable(variable);
        return rcaller.getParser().getAsDoubleArray(variable);
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        //not using rcaller getDoubleMatrix since it transposes the matrix as a side effect...
        final double[] ct = getDoubleVector(variable);
        if (ct == null) {
            return null;
        }
        final int[] ds = rcaller.getParser().getDimensions(variable);
        if ((ds == null) || (ds.length != 2)) {
            return null;
        }
        final int m = ds[0];
        final int n = ds[1];
        final double[][] r = new double[m][n];

        int i = 0;
        int k = 0;
        while (i < n) {
            int j = 0;
            while (j < m) {
                r[(j++)][i] = ct[(k++)];
            }
            i++;
        }
        return r;
    }

    @Override
    public int getInteger(final String variable) {
        requestVariable(variable);
        final int[] array = rcaller.getParser().getAsIntArray(variable);
        Assertions.checkEquals(array.length, 1);
        return array[0];
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        requestVariable(variable);
        return rcaller.getParser().getAsIntArray(variable);
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        final int[] ct = getIntegerVector(variable);
        if (ct == null) {
            return null;
        }
        final int[] ds = rcaller.getParser().getDimensions(variable);
        if ((ds == null) || (ds.length != 2)) {
            return null;
        }
        final int m = ds[0];
        final int n = ds[1];
        final int[][] r = new int[m][n];

        int i = 0;
        int k = 0;
        while (i < n) {
            int j = 0;
            while (j < m) {
                r[(j++)][i] = ct[(k++)];
            }
            i++;
        }
        return r;
    }

    @Override
    public boolean getBoolean(final String variable) {
        requestVariable(variable);
        final boolean[] array = rcaller.getParser().getAsLogicalArray(variable);
        Assertions.checkEquals(array.length, 1);
        return array[0];
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        requestVariable(variable);
        return rcaller.getParser().getAsLogicalArray(variable);
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        rcaller.getRCode().addRCode(CliScriptTaskRunnerPython.INTERNAL_RESULT_VARIABLE + " <- array(as.numeric(" + variable
                + "), dim(" + variable + "))");
        final double[][] matrix = getDoubleMatrix(CliScriptTaskRunnerPython.INTERNAL_RESULT_VARIABLE);
        final boolean[][] booleanMatrix = new boolean[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            final double[] vector = matrix[i];
            final boolean[] booleanVector = new boolean[vector.length];
            for (int j = 0; j < vector.length; j++) {
                booleanVector[j] = vector[j] > 0;
            }
            booleanMatrix[i] = booleanVector;
        }
        return booleanMatrix;
    }

}