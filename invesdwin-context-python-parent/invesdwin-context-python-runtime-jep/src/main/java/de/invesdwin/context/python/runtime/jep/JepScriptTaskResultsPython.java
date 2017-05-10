package de.invesdwin.context.python.runtime.jep;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskResults;
import jep.Jep;
import jep.JepException;

@NotThreadSafe
public class JepScriptTaskResultsPython implements IScriptTaskResults {

    private Jep jep;

    public JepScriptTaskResultsPython(final Jep jep) {
        this.jep = jep;
    }

    @Override
    public void close() {
        jep = null;
    }

    @Override
    public Jep getEngine() {
        return jep;
    }

    @Override
    public String getString(final String variable) {
        try {
            return (String) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getStringVector(final String variable) {
        try {
            return (String[]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        try {
            return (String[][]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(final String variable) {
        try {
            return (double) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        try {
            return (double[]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        try {
            return (double[][]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInteger(final String variable) {
        try {
            return (int) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        try {
            return (int[]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        try {
            return (int[][]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(final String variable) {
        try {
            return (boolean) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        try {
            return (boolean[]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        try {
            return (boolean[][]) jep.getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

}