package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;
import javax.script.ScriptException;

import de.invesdwin.context.r.runtime.contract.IScriptTaskResultsPython;
import de.invesdwin.util.math.Floats;
import de.invesdwin.util.math.Integers;
import de.invesdwin.util.math.Shorts;

@NotThreadSafe
public class JythonScriptTaskResultsPython implements IScriptTaskResultsPython {

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
    public float getFloat(final String variable) {
        try {
            return Floats.checkedCast(engine.unwrap().eval(variable));
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float[] getFloatVector(final String variable) {
        try {
            return Floats.checkedCastVector(engine.unwrap().eval(variable));
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float[][] getFloatMatrix(final String variable) {
        try {
            return Floats.checkedCastMatrix(engine.unwrap().eval(variable));
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
    public short getShort(final String variable) {
        try {
            final Number number = (Number) engine.unwrap().eval(variable);
            return Shorts.checkedCast(number);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short[] getShortVector(final String variable) {
        try {
            return (short[]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short[][] getShortMatrix(final String variable) {
        try {
            return (short[][]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInteger(final String variable) {
        try {
            final Number number = (Number) engine.unwrap().eval(variable);
            return Integers.checkedCast(number);
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
    public long getLong(final String variable) {
        try {
            final Number value = (Number) engine.unwrap().eval(variable);
            return value.longValue();
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long[] getLongVector(final String variable) {
        try {
            return (long[]) engine.unwrap().eval(variable);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long[][] getLongMatrix(final String variable) {
        try {
            return (long[][]) engine.unwrap().eval(variable);
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

}