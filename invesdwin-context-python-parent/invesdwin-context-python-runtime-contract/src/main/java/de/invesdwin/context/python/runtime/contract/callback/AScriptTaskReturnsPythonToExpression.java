package de.invesdwin.context.python.runtime.contract.callback;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.callback.IScriptTaskReturns;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.math.Doubles;

@NotThreadSafe
public abstract class AScriptTaskReturnsPythonToExpression implements IScriptTaskReturns {

    @Override
    public void returnCharacter(final char value) {
        returnExpression("'" + value + "'");
    }

    @Override
    public void returnCharacterVector(final char[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("'");
                sb.append(value[i]);
                sb.append("'");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnCharacterMatrix(final char[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final char[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append("'");
                    sb.append(valueRow[col]);
                    sb.append("'");
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnString(final String value) {
        if (value == null) {
            returnNull();
        } else {
            returnExpression("\"" + value + "\"");
        }
    }

    @Override
    public void returnStringVector(final String[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                final String v = value[i];
                if (v == null) {
                    sb.append("None");
                } else {
                    sb.append("\"");
                    sb.append(v);
                    sb.append("\"");
                }
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnStringMatrix(final String[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final String[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    final String v = valueRow[col];
                    if (v == null) {
                        sb.append("None");
                    } else {
                        sb.append("\"");
                        sb.append(v);
                        sb.append("\"");
                    }
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnBoolean(final boolean value) {
        returnExpression(booleanToString(value));
    }

    private String booleanToString(final boolean value) {
        if (value) {
            return "True";
        } else {
            return "False";
        }
    }

    @Override
    public void returnBooleanVector(final boolean[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(booleanToString(value[i]));
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnBooleanMatrix(final boolean[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final boolean[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append(booleanToString(valueRow[col]));
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    public void returnEmptyMatrix(final int rows) {
        final StringBuilder sb = new StringBuilder("[");
        for (int row = 0; row < rows; row++) {
            if (row > 0) {
                sb.append(",");
            }
            sb.append("[]");
        }
        sb.append("]");
        returnExpression(sb.toString());
    }

    @Override
    public void returnByte(final byte value) {
        returnExpression(String.valueOf(value));
    }

    @Override
    public void returnByteVector(final byte[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnByteMatrix(final byte[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final byte[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append(valueRow[col]);
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnShort(final short value) {
        returnExpression(String.valueOf(value));
    }

    @Override
    public void returnShortVector(final short[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnShortMatrix(final short[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final short[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append(valueRow[col]);
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnInteger(final int value) {
        returnExpression(String.valueOf(value));
    }

    @Override
    public void returnIntegerVector(final int[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnIntegerMatrix(final int[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final int[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append(valueRow[col]);
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnLong(final long value) {
        returnExpression(String.valueOf(value));
    }

    @Override
    public void returnLongVector(final long[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnLongMatrix(final long[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final long[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append(valueRow[col]);
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnFloat(final float value) {
        returnExpression(String.valueOf(value));
    }

    @Override
    public void returnFloatVector(final float[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(value[i]);
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnFloatMatrix(final float[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final float[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    sb.append(valueRow[col]);
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnDouble(final double value) {
        returnExpression(doubleToString(value));
    }

    private String doubleToString(final double value) {
        if (Doubles.isNaN(value)) {
            return "float('nan')";
        } else {
            return String.valueOf(value);
        }
    }

    @Override
    public void returnDoubleVector(final double[] value) {
        if (value == null) {
            returnNull();
        } else {
            final StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < value.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                final double v = value[i];
                sb.append(doubleToString(v));
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnDoubleMatrix(final double[][] value) {
        if (value == null) {
            returnNull();
        } else if (value.length == 0 || value[0].length == 0) {
            returnEmptyMatrix(value.length);
        } else {
            final int rows = value.length;
            final int cols = value[0].length;
            final StringBuilder sb = new StringBuilder("[");
            for (int row = 0; row < rows; row++) {
                final double[] valueRow = value[row];
                Assertions.checkEquals(valueRow.length, cols);
                if (row > 0) {
                    sb.append(",");
                }
                sb.append("[");
                for (int col = 0; col < cols; col++) {
                    if (col > 0) {
                        sb.append(",");
                    }
                    final double v = valueRow[col];
                    sb.append(doubleToString(v));
                }
                sb.append("]");
            }
            sb.append("]");
            returnExpression(sb.toString());
        }
    }

    @Override
    public void returnNull() {
        returnExpression("None");
    }

}
