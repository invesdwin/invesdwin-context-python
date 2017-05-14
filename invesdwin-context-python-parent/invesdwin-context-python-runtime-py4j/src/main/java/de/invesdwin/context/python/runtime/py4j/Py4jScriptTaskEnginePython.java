package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.py4j.pool.internal.Py4jInterpreter;

@NotThreadSafe
public class Py4jScriptTaskEnginePython implements IScriptTaskEngine {

    private Py4jInterpreter py4jInterpreter;
    private final Py4jScriptTaskInputsPython inputs;
    private final Py4jScriptTaskResultsPython results;

    public Py4jScriptTaskEnginePython(final Py4jInterpreter py4jInterpreter) {
        this.py4jInterpreter = py4jInterpreter;
        this.inputs = new Py4jScriptTaskInputsPython(this);
        this.results = new Py4jScriptTaskResultsPython(this);
    }

    @Override
    public void eval(final String expression) {
        py4jInterpreter.eval(expression);
    }

    @Override
    public Py4jScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public Py4jScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        py4jInterpreter = null;
    }

    @Override
    public Py4jInterpreter unwrap() {
        return py4jInterpreter;
    }

}
