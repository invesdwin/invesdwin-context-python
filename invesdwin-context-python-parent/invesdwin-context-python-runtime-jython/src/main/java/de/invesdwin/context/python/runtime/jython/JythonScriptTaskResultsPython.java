package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;
import javax.script.ScriptException;

import de.invesdwin.context.integration.script.IScriptTaskResults;

@NotThreadSafe
public class JythonScriptTaskResultsPython implements IScriptTaskResults {

    private final JythonScriptTaskEnginePython engine;

    public JythonScriptTaskResultsPython(final JythonScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JythonScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public String getString(final String variable) {
        try {
            return (String) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getStringVector(final String variable) {
        try {
            return (String[]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        try {
            return (String[][]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(final String variable) {
        try {
            return (double) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        try {
            return (double[]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        try {
            return (double[][]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInteger(final String variable) {
        try {
            final Number value = (Number) engine.unwrap().eval(variable);
            return value.intValue();
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        try {
            return (int[]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        try {
            return (int[][]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(final String variable) {
        try {
            return (boolean) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        try {
            return (boolean[]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        try {
            return (boolean[][]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isDefined(final String variable) {
        return getBoolean("'" + variable + "' in locals()");
    }

    @Override
    public boolean isNull(final String variable) {
        return getBoolean(variable + " is None");
    }

}