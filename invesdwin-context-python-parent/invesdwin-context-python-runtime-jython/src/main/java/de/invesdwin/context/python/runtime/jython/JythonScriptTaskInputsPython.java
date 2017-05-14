package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.contract.IScriptTaskInputsPython;

@NotThreadSafe
public class JythonScriptTaskInputsPython implements IScriptTaskInputsPython {

    private final JythonScriptTaskEnginePython engine;

    public JythonScriptTaskInputsPython(final JythonScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JythonScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public void putByte(final String variable, final byte value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putByteVector(final String variable, final byte[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putByteMatrix(final String variable, final byte[][] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putCharacter(final String variable, final char value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putCharacterVector(final String variable, final char[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putCharacterMatrix(final String variable, final char[][] value) {
        engine.unwrap().put(variable, value);
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
    public void putShort(final String variable, final short value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putShortVector(final String variable, final short[] value) {
        engine.unwrap().put(variable, value);
    }

    @Override
    public void putShortMatrix(final String variable, final short[][] value) {
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

}
