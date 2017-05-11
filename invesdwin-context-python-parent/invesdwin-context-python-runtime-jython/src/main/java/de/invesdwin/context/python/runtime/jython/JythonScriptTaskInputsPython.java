package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskInputs;

@NotThreadSafe
public class JythonScriptTaskInputsPython implements IScriptTaskInputs {

    private final JythonScriptTaskEnginePython engine;

    public JythonScriptTaskInputsPython(final JythonScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JythonScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public void putString(final String variable, final String value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putFloat(final String variable, final float value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putFloatVector(final String variable, final float[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putFloatMatrix(final String variable, final float[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putDouble(final String variable, final double value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putDoubleMatrix(final String variable, final double[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putInteger(final String variable, final int value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putIntegerMatrix(final String variable, final int[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putLong(final String variable, final long value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putLongVector(final String variable, final long[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putLongMatrix(final String variable, final long[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putExpression(final String variable, final String expression) {
        engine.eval(variable + " = " + expression);
    }

}
