package de.invesdwin.context.python.runtime.jep;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskInputs;
import jep.Jep;
import jep.JepException;

@NotThreadSafe
public class JepScriptTaskInputsPython implements IScriptTaskInputs {

    private Jep jep;

    public JepScriptTaskInputsPython(final Jep jep) {
        this.jep = jep;
    }

    @Override
    public Jep getEngine() {
        return jep;
    }

    @Override
    public void close() {
        jep = null;
    }

    @Override
    public void putString(final String variable, final String value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putDouble(final String variable, final double value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        try {
            jep.set(variable, value);
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
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putInteger(final String variable, final int value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        try {
            jep.set(variable, value);
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
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        try {
            jep.set(variable, value);
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putExpression(final String variable, final String expression) {
        JepScriptTaskRunnerPython.eval(jep, variable + " = " + expression);
    }

}
