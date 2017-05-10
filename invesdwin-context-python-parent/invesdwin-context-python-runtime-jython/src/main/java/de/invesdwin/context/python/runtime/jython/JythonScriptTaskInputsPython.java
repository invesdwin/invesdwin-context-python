package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;
import javax.script.ScriptException;

import org.python.jsr223.PyScriptEngine;

import de.invesdwin.context.integration.script.IScriptTaskInputs;

@NotThreadSafe
public class JythonScriptTaskInputsPython implements IScriptTaskInputs {

    private PyScriptEngine pyScriptEngine;

    public JythonScriptTaskInputsPython(final PyScriptEngine pyScriptEngine) {
        this.pyScriptEngine = pyScriptEngine;
    }

    @Override
    public PyScriptEngine getEngine() {
        return pyScriptEngine;
    }

    @Override
    public void close() {
        pyScriptEngine = null;
    }

    @Override
    public void putString(final String variable, final String value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putDouble(final String variable, final double value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putDoubleMatrix(final String variable, final double[][] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putInteger(final String variable, final int value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putIntegerMatrix(final String variable, final int[][] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        pyScriptEngine.put(variable, value);
    }

    @Override
    public void putExpression(final String variable, final String expression) {
        try {
            pyScriptEngine.eval(variable + " = " + expression);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

}
