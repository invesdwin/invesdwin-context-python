package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.contract.IScriptTaskResultsPython;
import de.invesdwin.util.lang.string.Strings;
import de.invesdwin.util.math.Booleans;
import de.invesdwin.util.math.Bytes;
import de.invesdwin.util.math.Characters;
import de.invesdwin.util.math.Doubles;
import de.invesdwin.util.math.Floats;
import de.invesdwin.util.math.Integers;
import de.invesdwin.util.math.Longs;
import de.invesdwin.util.math.Shorts;

@NotThreadSafe
public class Py4jScriptTaskResultsPython implements IScriptTaskResultsPython {

    private final Py4jScriptTaskEnginePython engine;

    public Py4jScriptTaskResultsPython(final Py4jScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public Py4jScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public byte getByte(final String variable) {
        return Bytes.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public byte[] getByteVector(final String variable) {
        return Bytes.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public byte[][] getByteMatrix(final String variable) {
        return Bytes.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public char getCharacter(final String variable) {
        return Characters.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public char[] getCharacterVector(final String variable) {
        return Characters.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public char[][] getCharacterMatrix(final String variable) {
        return Characters.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public String getString(final String variable) {
        return Strings.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public String[] getStringVector(final String variable) {
        return Strings.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        return Strings.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public float getFloat(final String variable) {
        return Floats.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public float[] getFloatVector(final String variable) {
        return Floats.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public float[][] getFloatMatrix(final String variable) {
        return Floats.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public double getDouble(final String variable) {
        return Doubles.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        return Doubles.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        return Doubles.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public short getShort(final String variable) {
        return Shorts.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public short[] getShortVector(final String variable) {
        return Shorts.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public short[][] getShortMatrix(final String variable) {
        return Shorts.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public int getInteger(final String variable) {
        return Integers.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        return Integers.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        return Integers.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public long getLong(final String variable) {
        return Longs.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public long[] getLongVector(final String variable) {
        return Longs.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public long[][] getLongMatrix(final String variable) {
        return Longs.checkedCastMatrix(engine.unwrap().get(variable));
    }

    @Override
    public boolean getBoolean(final String variable) {
        return Booleans.checkedCast(engine.unwrap().get(variable));
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        return Booleans.checkedCastVector(engine.unwrap().get(variable));
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        return Booleans.checkedCastMatrix(engine.unwrap().get(variable));
    }

}