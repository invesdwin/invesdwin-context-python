package de.invesdwin.context.python.runtime.jep;

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
import jep.JepException;

@NotThreadSafe
public class JepScriptTaskResultsPython implements IScriptTaskResultsPython {

    private final JepScriptTaskEnginePython engine;

    public JepScriptTaskResultsPython(final JepScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JepScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public byte getByte(final String variable) {
        try {
            return Bytes.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getByteVector(final String variable) {
        try {
            return Bytes.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[][] getByteMatrix(final String variable) {
        try {
            return Bytes.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char getCharacter(final String variable) {
        try {
            return Characters.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char[] getCharacterVector(final String variable) {
        try {
            return Characters.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char[][] getCharacterMatrix(final String variable) {
        try {
            return Characters.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(final String variable) {
        try {
            return Strings.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getStringVector(final String variable) {
        try {
            return Strings.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        try {
            return Strings.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float getFloat(final String variable) {
        try {
            return Floats.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float[] getFloatVector(final String variable) {
        try {
            return Floats.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float[][] getFloatMatrix(final String variable) {
        try {
            return Floats.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(final String variable) {
        try {
            return Doubles.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        try {
            return Doubles.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        try {
            return Doubles.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short getShort(final String variable) {
        try {
            return Shorts.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short[] getShortVector(final String variable) {
        try {
            return Shorts.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short[][] getShortMatrix(final String variable) {
        try {
            return Shorts.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInteger(final String variable) {
        try {
            return Integers.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        try {
            return Integers.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        try {
            return Integers.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getLong(final String variable) {
        try {
            return Longs.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long[] getLongVector(final String variable) {
        try {
            return Longs.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long[][] getLongMatrix(final String variable) {
        try {
            return Longs.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(final String variable) {
        try {
            return Booleans.checkedCast(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        try {
            return Booleans.checkedCastVector(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        try {
            return Booleans.checkedCastMatrix(engine.unwrap().getValue(variable));
        } catch (final JepException e) {
            throw new RuntimeException(e);
        }
    }

}