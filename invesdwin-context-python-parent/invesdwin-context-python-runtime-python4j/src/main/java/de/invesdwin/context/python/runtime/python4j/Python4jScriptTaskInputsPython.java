package de.invesdwin.context.python.runtime.python4j;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.contract.IScriptTaskInputsPython;
import de.invesdwin.util.lang.string.Strings;
import de.invesdwin.util.math.Integers;

@NotThreadSafe
public class Python4jScriptTaskInputsPython implements IScriptTaskInputsPython {

    private final Python4jScriptTaskEnginePython engine;

    public Python4jScriptTaskInputsPython(final Python4jScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public Python4jScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public void putByte(final String variable, final byte value) {
        putInteger(variable, Integers.checkedCast(value));
    }

    @Override
    public void putByteVector(final String variable, final byte[] value) {
        putIntegerVector(variable, Integers.checkedCastVector(value));
    }

    @Override
    public void putByteMatrix(final String variable, final byte[][] value) {
        putIntegerMatrix(variable, Integers.checkedCastMatrix(value));
    }

    @Override
    public void putCharacter(final String variable, final char value) {
        putString(variable, Strings.checkedCast(value));
    }

    @Override
    public void putCharacterVector(final String variable, final char[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            putStringVector(variable, Strings.checkedCastVector(value));
        }
    }

    @Override
    public void putCharacterMatrix(final String variable, final char[][] value) {
        if (value == null) {
            putNull(variable);
        } else {
            putStringMatrix(variable, Strings.checkedCastMatrix(value));
        }
    }

    @Override
    public void putString(final String variable, final String value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putFloat(final String variable, final float value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putFloatVector(final String variable, final float[] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putFloatMatrix(final String variable, final float[][] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putDouble(final String variable, final double value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putDoubleMatrix(final String variable, final double[][] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putShort(final String variable, final short value) {
        putInteger(variable, Integers.checkedCast(value));
    }

    @Override
    public void putShortVector(final String variable, final short[] value) {
        putIntegerVector(variable, Integers.checkedCastVector(value));
    }

    @Override
    public void putShortMatrix(final String variable, final short[][] value) {
        putIntegerMatrix(variable, Integers.checkedCastMatrix(value));
    }

    @Override
    public void putInteger(final String variable, final int value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putIntegerMatrix(final String variable, final int[][] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putLong(final String variable, final long value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putLongVector(final String variable, final long[] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putLongMatrix(final String variable, final long[][] value) {
        engine.unwrap().set(variable, value);
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        engine.unwrap().set(variable, value);
        putExpression(variable, "bool(" + variable + ")");
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        engine.unwrap().set(variable, value);
        if (value != null) {
            putExpression(variable, "[bool(x) for x in " + variable + "]");
        }
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        engine.unwrap().set(variable, value);
        if (value != null) {
            putExpression(variable, "[[bool(y) for y in x] for x in " + variable + "]");
        }
    }

}
