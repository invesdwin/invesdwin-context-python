package de.invesdwin.context.python.runtime.jep;

import javax.annotation.concurrent.NotThreadSafe;

import org.rosuda.JRI.RBool;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class JepScriptTaskResultsPython implements IScriptTaskResults {

    private Rengine rengine;

    public JepScriptTaskResultsPython(final Rengine rengine) {
        this.rengine = rengine;
    }

    @Override
    public void close() {
        rengine = null;
    }

    @Override
    public Rengine getEngine() {
        return rengine;
    }

    @Override
    public String getString(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return rexp.asString();
    }

    @Override
    public String[] getStringVector(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return rexp.asStringArray();
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return asStringMatrix(rexp);
    }

    private String[][] asStringMatrix(final REXP rexp) {
        final String[] ct = rexp.asStringArray();
        if (ct == null) {
            return null;
        }
        final REXP dim = rexp.getAttribute("dim");
        if (dim == null) {
            return null;
        }
        final int[] ds = dim.asIntArray();
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
        final REXP rexp = rengine.eval(variable);
        return rexp.asDouble();
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return rexp.asDoubleArray();
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return rexp.asDoubleMatrix();
    }

    @Override
    public int getInteger(final String variable) {
        final REXP rexp = rengine.eval(variable);
        final int[] array = rexp.asIntArray();
        Assertions.checkEquals(array.length, 1);
        return array[0];
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return rexp.asIntArray();
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        final REXP rexp = rengine.eval(variable);
        return asIntMatrix(rexp);
    }

    private int[][] asIntMatrix(final REXP rexp) {
        final int[] ct = rexp.asIntArray();
        if (ct == null) {
            return null;
        }
        final REXP dim = rexp.getAttribute("dim");
        if (dim == null) {
            return null;
        }
        final int[] ds = dim.asIntArray();
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
        final REXP rexp = rengine.eval(variable);
        final RBool bool = rexp.asBool();
        return bool.isTRUE();
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        final REXP rexp = rengine.eval(variable);
        final int[] boolArray = rexp.asIntArray();
        final boolean[] booleanVector = new boolean[boolArray.length];
        for (int i = 0; i < boolArray.length; i++) {
            booleanVector[i] = boolArray[i] > 0;
        }
        return booleanVector;
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        final REXP rexp = rengine.eval(variable);
        final int[][] matrix = asIntMatrix(rexp);
        final boolean[][] booleanMatrix = new boolean[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            final int[] vector = matrix[i];
            final boolean[] booleanVector = new boolean[vector.length];
            for (int j = 0; j < vector.length; j++) {
                booleanVector[j] = vector[j] > 0;
            }
            booleanMatrix[i] = booleanVector;
        }
        return booleanMatrix;
    }

}