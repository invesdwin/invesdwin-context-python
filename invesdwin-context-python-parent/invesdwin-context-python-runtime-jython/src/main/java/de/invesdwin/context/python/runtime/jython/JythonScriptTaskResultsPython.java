package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;

import org.math.R.Rsession;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class JythonScriptTaskResultsPython implements IScriptTaskResults {

    private Rsession rsession;

    public JythonScriptTaskResultsPython(final Rsession rsession) {
        this.rsession = rsession;
    }

    @Override
    public Rsession getEngine() {
        return rsession;
    }

    @Override
    public void close() {
        rsession = null;
    }

    @Override
    public String getString(final String variable) {
        try {
            final REXP rexp = rsession.eval(variable);
            final boolean[] na = rexp.isNA();
            Assertions.checkEquals(na.length, 1);
            if (na[0]) {
                return null;
            } else {
                return rexp.asString();
            }
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getStringVector(final String variable) {
        try {
            return rsession.eval(variable).asStrings();
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        try {
            final REXP rexp = rsession.eval(variable);
            return asStringMatrix(rexp);
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private String[][] asStringMatrix(final REXP rexp) throws REXPMismatchException {
        final String[] ct = rexp.asStrings();
        final REXP dim = rexp.getAttribute("dim");
        if (dim == null) {
            throw new REXPMismatchException(rexp, "matrix (dim attribute missing)");
        }
        final int[] ds = dim.asIntegers();
        if (ds.length != 2) {
            throw new REXPMismatchException(rexp, "matrix (wrong dimensionality)");
        }
        final int m = ds[0], n = ds[1];

        final String[][] r = new String[m][n];
        // R stores matrices as matrix(c(1,2,3,4),2,2) = col1:(1,2), col2:(3,4)
        // we need to copy everything, since we create 2d array from 1d array
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                r[j][i] = ct[k++];
            }
        }
        return r;
    }

    @Override
    public double getDouble(final String variable) {
        try {
            return rsession.eval(variable).asDouble();
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        try {
            return rsession.eval(variable).asDoubles();
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        try {
            return rsession.eval(variable).asDoubleMatrix();
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInteger(final String variable) {
        try {
            return rsession.eval(variable).asInteger();
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        try {
            return rsession.eval(variable).asIntegers();
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        try {
            return asIntegerMatrix(rsession.eval(variable));
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private int[][] asIntegerMatrix(final REXP rexp) throws REXPMismatchException {
        final int[] ct = rexp.asIntegers();
        final REXP dim = rexp.getAttribute("dim");
        if (dim == null) {
            throw new REXPMismatchException(rexp, "matrix (dim attribute missing)");
        }
        final int[] ds = dim.asIntegers();
        if (ds.length != 2) {
            throw new REXPMismatchException(rexp, "matrix (wrong dimensionality)");
        }
        final int m = ds[0], n = ds[1];

        final int[][] r = new int[m][n];
        // R stores matrices as matrix(c(1,2,3,4),2,2) = col1:(1,2), col2:(3,4)
        // we need to copy everything, since we create 2d array from 1d array
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                r[j][i] = ct[k++];
            }
        }
        return r;
    }

    @Override
    public boolean getBoolean(final String variable) {
        try {
            return rsession.eval(variable).asInteger() > 0;
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        try {
            final int[] ints = rsession.eval(variable).asIntegers();
            final boolean[] booleanVector = new boolean[ints.length];
            for (int i = 0; i < ints.length; i++) {
                booleanVector[i] = ints[i] > 0;
            }
            return booleanVector;
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        try {
            final double[][] matrix = rsession.eval(variable).asDoubleMatrix();
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
        } catch (final REXPMismatchException e) {
            throw new RuntimeException(e);
        }
    }

}