package de.invesdwin.context.python.runtime.jep;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskInputs;
import jep.JepException;

@NotThreadSafe
public class JepScriptTaskInputsPython implements IScriptTaskInputs {

    private final JepScriptTaskEnginePython engine;

    public JepScriptTaskInputsPython(final JepScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JepScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public void putString(final String variable, final String value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putDouble(final String variable, final double value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * http://permalink.gmane.org/gmane.comp.lang.r.rosuda.devel/87
     */
    @Override
    public void putDoubleMatrix(final String variable, final double[][] value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putInteger(final String variable, final int value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * http://permalink.gmane.org/gmane.comp.lang.r.rosuda.devel/87
     */
    @Override
    public void putIntegerMatrix(final String variable, final int[][] value) {
        try {
            engine.unwrap().set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        try {
            engine.unwrap().set(variable, value);
            putExpression(variable, "bool(" + variable + ")");
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        try {
            engine.unwrap().set(variable, value);
            putExpression(variable, "[bool(x) for x in " + variable + "]");
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        try {
            engine.unwrap().set(variable, value);
            putExpression(variable, "[[bool(y) for y in x] for x in " + variable + "]");
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putExpression(final String variable, final String expression) {
        engine.eval(variable + " = " + expression);
    }

}
