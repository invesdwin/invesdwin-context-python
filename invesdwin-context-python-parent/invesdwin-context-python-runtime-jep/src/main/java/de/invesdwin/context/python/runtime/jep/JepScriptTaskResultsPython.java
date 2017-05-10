package de.invesdwin.context.python.runtime.jep;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.ArrayUtils;

import de.invesdwin.context.integration.script.IScriptTaskResults;
import jep.JepException;

@NotThreadSafe
public class JepScriptTaskResultsPython implements IScriptTaskResults {

    private final JepScriptTaskEnginePython engine;

    public JepScriptTaskResultsPython(final JepScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JepScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public String getString(final String variable) {
        try {
            return (String) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getStringVector(final String variable) {
        try {
            return (String[]) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        try {
            return (String[][]) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(final String variable) {
        try {
            return (double) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        try {
            return (double[]) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        try {
            return (double[][]) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInteger(final String variable) {
        try {
            final Number value = (Number) engine.unwrap().getValue(variable);
            return value.intValue();
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        try {
            return (int[]) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        try {
            return (int[][]) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(final String variable) {
        try {
            return (boolean) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Boolean> getBooleanVectorAsList(final String variable) {
        try {
            return (List<Boolean>) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        final List<Boolean> value = getBooleanVectorAsList(variable);
        return ArrayUtils.toPrimitive(value.toArray(new Boolean[value.size()]));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<List<Boolean>> getBooleanMatrixAsList(final String variable) {
        try {
            return (List<List<Boolean>>) engine.unwrap().getValue(variable);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        final List<List<Boolean>> value = getBooleanMatrixAsList(variable);
        final boolean[][] matrix = new boolean[value.size()][];
        for (int i = 0; i < value.size(); i++) {
            final List<Boolean> vector = value.get(i);
            matrix[i] = ArrayUtils.toPrimitive(vector.toArray(new Boolean[vector.size()]));
        }
        return matrix;
    }

}