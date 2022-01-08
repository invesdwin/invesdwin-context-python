package de.invesdwin.context.python.runtime.python4j;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.python4j.internal.IPythonEngineWrapper;
import de.invesdwin.context.python.runtime.python4j.internal.InitializingPythonEngineWrapper;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;

@NotThreadSafe
public class Python4jScriptTaskEnginePython implements IScriptTaskEngine {

    private IPythonEngineWrapper pythonEngine;
    private final Python4jScriptTaskInputsPython inputs;
    private final Python4jScriptTaskResultsPython results;

    public Python4jScriptTaskEnginePython(final IPythonEngineWrapper pythonEngine) {
        this.pythonEngine = pythonEngine;
        this.inputs = new Python4jScriptTaskInputsPython(this);
        this.results = new Python4jScriptTaskResultsPython(this);
    }

    @Override
    public void eval(final String expression) {
        try {
            pythonEngine.exec(expression);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Python4jScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public Python4jScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        if (pythonEngine != null) {
            eval("restoreContext()");
            pythonEngine = null;
        }
    }

    @Override
    public IPythonEngineWrapper unwrap() {
        return pythonEngine;
    }

    @Override
    public ILock getSharedLock() {
        if (pythonEngine == null) {
            return DisabledLock.INSTANCE;
        } else {
            return pythonEngine.getLock();
        }
    }

    @Override
    public WrappedExecutorService getSharedExecutor() {
        return null;
    }

    /**
     * WARNING: Don't share instances of this class between threads, or else deadlocks or jvm crashes might occur due to
     * GIL lock mismanagement.
     */
    public static Python4jScriptTaskEnginePython newInstance() {
        return new Python4jScriptTaskEnginePython(InitializingPythonEngineWrapper.getInstance());
    }

}
