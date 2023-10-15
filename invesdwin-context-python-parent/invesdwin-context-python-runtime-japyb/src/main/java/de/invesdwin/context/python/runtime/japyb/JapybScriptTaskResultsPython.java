package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.NotThreadSafe;

import com.fasterxml.jackson.databind.JsonNode;

import de.invesdwin.context.python.runtime.contract.IScriptTaskResultsPython;
import de.invesdwin.util.lang.string.Strings;
import de.invesdwin.util.math.Booleans;
import de.invesdwin.util.math.Bytes;
import de.invesdwin.util.math.Characters;
import de.invesdwin.util.math.Integers;
import de.invesdwin.util.math.Longs;
import de.invesdwin.util.math.Shorts;

@NotThreadSafe
public class JapybScriptTaskResultsPython implements IScriptTaskResultsPython {

    private final JapybScriptTaskEnginePython engine;

    public JapybScriptTaskResultsPython(final JapybScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JapybScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public String getString(final String variable) {
        final JsonNode node = engine.unwrap().getAsJsonNode(variable);
        if (node == null) {
            return null;
        }
        final String str = node.asText();
        if (Strings.isBlankOrNullText(str)) {
            return null;
        } else {
            return str;
        }
    }

    @Override
    public String[] getStringVector(final String variable) {
        JsonNode strs = engine.unwrap().getAsJsonNode(variable);
        if (strs == null) {
            return null;
        }
        //unwrap array
        while (strs.size() == 1 && strs.get(0).size() > 1) {
            strs = strs.get(0);
        }
        final String[] values = new String[strs.size()];
        for (int i = 0; i < values.length; i++) {
            final String str = strs.get(i).asText();
            if (Strings.isBlankOrNullText(str)) {
                values[i] = null;
            } else {
                values[i] = str;
            }
        }
        return values;
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        //json returns the columns instead of rows
        final JsonNode strsMatrix = engine.unwrap().getAsJsonNode(variable);
        if (strsMatrix == null) {
            return null;
        }
        if (strsMatrix.size() == 0) {
            //https://stackoverflow.com/questions/23079625/extract-array-dimensions-in-julia
            final int[] dims = getIntegerVector("size(" + variable + ")");
            final int rows = dims[0];
            final String[][] emptyMatrix = new String[rows][];
            for (int i = 0; i < rows; i++) {
                emptyMatrix[i] = Strings.EMPTY_ARRAY;
            }
            return emptyMatrix;
        }
        //[11 12 13;21 22 23;31 32 33;41 42 43]
        //[[11,21,31,41],[12,22,32,42],[13,23,33,43]]
        final int columns = strsMatrix.size();
        final int rows = strsMatrix.get(0).size();
        final String[][] valuesMatrix = new String[rows][];
        for (int r = 0; r < rows; r++) {
            final String[] values = new String[columns];
            valuesMatrix[r] = values;
            for (int c = 0; c < columns; c++) {
                final String str = strsMatrix.get(c).get(r).asText();
                if (Strings.isBlankOrNullText(str)) {
                    values[c] = null;
                } else {
                    values[c] = str;
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public char getCharacter(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Characters.DEFAULT_MISSING_VALUE;
        } else {
            return Characters.checkedCast(str);
        }
    }

    @Override
    public char[] getCharacterVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final char[] values = new char[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Characters.DEFAULT_MISSING_VALUE;
            } else {
                values[i] = Characters.checkedCast(str);
            }
        }
        return values;
    }

    @Override
    public char[][] getCharacterMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final char[][] valuesMatrix = new char[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final char[] values = new char[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Characters.DEFAULT_MISSING_VALUE;
                } else {
                    values[j] = Characters.checkedCast(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public boolean getBoolean(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Booleans.DEFAULT_MISSING_VALUE;
        } else {
            return Boolean.parseBoolean(str);
        }
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final boolean[] values = new boolean[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Booleans.DEFAULT_MISSING_VALUE;
            } else {
                values[i] = Boolean.parseBoolean(str);
            }
        }
        return values;
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final boolean[][] valuesMatrix = new boolean[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final boolean[] values = new boolean[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Booleans.DEFAULT_MISSING_VALUE;
                } else {
                    values[j] = Boolean.parseBoolean(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public byte getByte(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Bytes.DEFAULT_MISSING_VALUE;
        } else {
            return Byte.parseByte(str);
        }
    }

    @Override
    public byte[] getByteVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final byte[] values = new byte[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Bytes.DEFAULT_MISSING_VALUE;
            } else {
                values[i] = Byte.parseByte(str);
            }
        }
        return values;
    }

    @Override
    public byte[][] getByteMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final byte[][] valuesMatrix = new byte[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final byte[] values = new byte[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Bytes.DEFAULT_MISSING_VALUE;
                } else {
                    values[j] = Byte.parseByte(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public short getShort(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Shorts.DEFAULT_MISSING_VALUE;
        } else {
            return Short.parseShort(str);
        }
    }

    @Override
    public short[] getShortVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final short[] values = new short[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Shorts.DEFAULT_MISSING_VALUE;
            } else {
                values[i] = Short.parseShort(str);
            }
        }
        return values;
    }

    @Override
    public short[][] getShortMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final short[][] valuesMatrix = new short[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final short[] values = new short[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Shorts.DEFAULT_MISSING_VALUE;
                } else {
                    values[j] = Short.parseShort(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public int getInteger(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Integers.DEFAULT_MISSING_VALUE;
        } else {
            return Integer.parseInt(str);
        }
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final int[] values = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Integers.DEFAULT_MISSING_VALUE;
            } else {
                values[i] = Integer.parseInt(str);
            }
        }
        return values;
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final int[][] valuesMatrix = new int[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final int[] values = new int[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Integers.DEFAULT_MISSING_VALUE;
                } else {
                    values[j] = Integer.parseInt(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public long getLong(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Longs.DEFAULT_MISSING_VALUE;
        } else {
            return Long.parseLong(str);
        }
    }

    @Override
    public long[] getLongVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final long[] values = new long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Longs.DEFAULT_MISSING_VALUE;
            } else {
                values[i] = Long.parseLong(str);
            }
        }
        return values;
    }

    @Override
    public long[][] getLongMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final long[][] valuesMatrix = new long[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final long[] values = new long[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Longs.DEFAULT_MISSING_VALUE;
                } else {
                    values[j] = Long.parseLong(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public float getFloat(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Float.NaN;
        } else {
            return Float.parseFloat(str);
        }
    }

    @Override
    public float[] getFloatVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final float[] values = new float[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Float.NaN;
            } else {
                values[i] = Float.parseFloat(str);
            }
        }
        return values;
    }

    @Override
    public float[][] getFloatMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final float[][] valuesMatrix = new float[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final float[] values = new float[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Float.NaN;
                } else {
                    values[j] = Float.parseFloat(str);
                }
            }
        }
        return valuesMatrix;
    }

    @Override
    public double getDouble(final String variable) {
        final String str = getString(variable);
        if (str == null) {
            return Double.NaN;
        } else {
            return Double.parseDouble(str);
        }
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        final String[] strs = getStringVector(variable);
        if (strs == null) {
            return null;
        }
        final double[] values = new double[strs.length];
        for (int i = 0; i < strs.length; i++) {
            final String str = strs[i];
            if (str == null) {
                values[i] = Double.NaN;
            } else {
                values[i] = Double.parseDouble(str);
            }
        }
        return values;
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        final String[][] strsMatrix = getStringMatrix(variable);
        if (strsMatrix == null) {
            return null;
        }
        final double[][] valuesMatrix = new double[strsMatrix.length][];
        for (int i = 0; i < strsMatrix.length; i++) {
            final String[] strs = strsMatrix[i];
            final double[] values = new double[strs.length];
            valuesMatrix[i] = values;
            for (int j = 0; j < strs.length; j++) {
                final String str = strs[j];
                if (str == null) {
                    values[j] = Double.NaN;
                } else {
                    values[j] = Double.parseDouble(str);
                }
            }
        }
        return valuesMatrix;
    }

}