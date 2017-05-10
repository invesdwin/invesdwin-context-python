package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;

import org.python.jsr223.PyScriptEngine;

import de.invesdwin.context.integration.script.IScriptTaskResults;

@NotThreadSafe
public class JythonScriptTaskResultsPython implements IScriptTaskResults {

    private PyScriptEngine pyScriptEngine;

    public JythonScriptTaskResultsPython(final PyScriptEngine pyScriptEngine) {
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
    public String getString(final String variable) {
        return (String) pyScriptEngine.get(variable);
    }

    @Override
    public String[] getStringVector(final String variable) {
        return (String[]) pyScriptEngine.get(variable);
    }

    @Override
    public String[][] getStringMatrix(final String variable) {
        return (String[][]) pyScriptEngine.get(variable);
    }

    @Override
    public double getDouble(final String variable) {
        return (double) pyScriptEngine.get(variable);
    }

    @Override
    public double[] getDoubleVector(final String variable) {
        return (double[]) pyScriptEngine.get(variable);
    }

    @Override
    public double[][] getDoubleMatrix(final String variable) {
        return (double[][]) pyScriptEngine.get(variable);
    }

    @Override
    public int getInteger(final String variable) {
        return (int) pyScriptEngine.get(variable);
    }

    @Override
    public int[] getIntegerVector(final String variable) {
        return (int[]) pyScriptEngine.get(variable);
    }

    @Override
    public int[][] getIntegerMatrix(final String variable) {
        return (int[][]) pyScriptEngine.get(variable);
    }

    @Override
    public boolean getBoolean(final String variable) {
        return (boolean) pyScriptEngine.get(variable);
    }

    @Override
    public boolean[] getBooleanVector(final String variable) {
        return (boolean[]) pyScriptEngine.get(variable);
    }

    @Override
    public boolean[][] getBooleanMatrix(final String variable) {
        return (boolean[][]) pyScriptEngine.get(variable);
    }

}