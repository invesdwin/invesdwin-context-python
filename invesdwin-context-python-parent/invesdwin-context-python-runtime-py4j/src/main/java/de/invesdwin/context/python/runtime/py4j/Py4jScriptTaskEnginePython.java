package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.py4j.pool.Py4jInterpreterObjectPool;
import de.invesdwin.context.python.runtime.py4j.pool.internal.Py4jInterpreter;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;

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

    /**
     * Each instance has its own engine, so no shared locking required.
     */
    @Override
    public ILock getSharedLock() {
        return DisabledLock.INSTANCE;
    }

    @Override
    public WrappedExecutorService getSharedExecutor() {
        return null;
    }

    public static Py4jScriptTaskEnginePython newInstance() {
        return new Py4jScriptTaskEnginePython(Py4jInterpreterObjectPool.INSTANCE.borrowObject()) {
            @Override
            public void close() {
                final Py4jInterpreter unwrap = unwrap();
                if (unwrap != null) {
                    Py4jInterpreterObjectPool.INSTANCE.returnObject(unwrap);
                }
                super.close();
            }
        };
    }

}
