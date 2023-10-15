package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.contract.IScriptTaskInputsPython;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class JapybScriptTaskInputsPython implements IScriptTaskInputsPython {

    private final JapybScriptTaskEnginePython engine;

    public JapybScriptTaskInputsPython(final JapybScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JapybScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    public void putCharacter(final String variable, final char value) {
        putExpression(variable, "Char('" + value + "')");
    }

    @Override
    public void putCharacterVector(final String variable, final char[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Char}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("'");
                sb.append(value[i]);
                sb.append("'");
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putCharacterMatrix(final String variable, final char[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Char}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Char}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append("'");
                    sb.append(value[row][col]);
                    sb.append("'");
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putString(final String variable, final String value) {
        if (value == null) {
            putNull(variable);
        } else {
            putExpression(variable, "String(\"" + value + "\")");
        }
    }

    @Override
    public void putStringVector(final String variable, final String[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{String}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                final String v = value[i];
                if (v == null) {
                    sb.append("\"\"");
                } else {
                    sb.append("\"");
                    sb.append(v);
                    sb.append("\"");
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putStringMatrix(final String variable, final String[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{String}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{String}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    final String v = value[row][col];
                    if (v == null) {
                        sb.append("\"\"");
                    } else {
                        sb.append("\"");
                        sb.append(v);
                        sb.append("\"");
                    }
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putBoolean(final String variable, final boolean value) {
        putExpression(variable, "Bool(" + String.valueOf(value) + ")");
    }

    @Override
    public void putBooleanVector(final String variable, final boolean[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Bool}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putBooleanMatrix(final String variable, final boolean[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Bool}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Bool}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putByte(final String variable, final byte value) {
        putExpression(variable, "Int8(" + String.valueOf(value) + ")");
    }

    @Override
    public void putByteVector(final String variable, final byte[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Int8}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putByteMatrix(final String variable, final byte[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Int8}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Int8}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putShort(final String variable, final short value) {
        putExpression(variable, "Int16(" + String.valueOf(value) + ")");
    }

    @Override
    public void putShortVector(final String variable, final short[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Int16}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putShortMatrix(final String variable, final short[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Int16}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Int16}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putInteger(final String variable, final int value) {
        putExpression(variable, "Int32(" + String.valueOf(value) + ")");
    }

    @Override
    public void putIntegerVector(final String variable, final int[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Int32}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putIntegerMatrix(final String variable, final int[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Int32}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Int32}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putLong(final String variable, final long value) {
        putExpression(variable, "Int64(" + String.valueOf(value) + ")");
    }

    @Override
    public void putLongVector(final String variable, final long[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Int64}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putLongMatrix(final String variable, final long[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Int64}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Int64}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putFloat(final String variable, final float value) {
        putExpression(variable, "Float32(" + String.valueOf(value) + ")");
    }

    @Override
    public void putFloatVector(final String variable, final float[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Float32}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putFloatMatrix(final String variable, final float[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Float32}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Float32}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putDouble(final String variable, final double value) {
        putExpression(variable, "Float64(" + String.valueOf(value) + ")");
    }

    @Override
    public void putDoubleVector(final String variable, final double[] value) {
        if (value == null) {
            putNull(variable);
        } else {
            final StringBuilder sb = new StringBuilder("Array{Float64}([");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

    @Override
    public void putDoubleMatrix(final String variable, final double[][] value) {
        if (value == null) {
            putNull(variable);
        } else if (value.length == 0 || value[0].length == 0) {
            putExpression(variable, "Array{Float64}(undef, " + value.length + ", 0)");
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("Array{Float64}([");
            for (int row = 0; row < rows; row++) {
                Assertions.checkEquals(value[row].length, cols);
                if (row > 0) {
                    sb.append(";");
                }
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(" ");
                    }
                    sb.append(value[row][col]);
                }
            }
            sb.append("])");
            putExpression(variable, sb.toString());
        }
    }

}
