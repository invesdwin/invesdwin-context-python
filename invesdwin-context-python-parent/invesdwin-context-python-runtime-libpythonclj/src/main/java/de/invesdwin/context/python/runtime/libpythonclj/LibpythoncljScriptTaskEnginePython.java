package de.invesdwin.context.python.runtime.libpythonclj;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.libpythonclj.internal.IPythonEngineWrapper;
import de.invesdwin.context.python.runtime.libpythonclj.internal.InitializingPythonEngineWrapper;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;

@NotThreadSafe
public class LibpythoncljScriptTaskEnginePython implements IScriptTaskEngine {

    private IPythonEngineWrapper pythonEngine;
    private final LibpythoncljScriptTaskInputsPython inputs;
    private final LibpythoncljScriptTaskResultsPython results;

    public LibpythoncljScriptTaskEnginePython(final IPythonEngineWrapper pythonEngine) {
        this.pythonEngine = pythonEngine;
        this.inputs = new LibpythoncljScriptTaskInputsPython(this);
        this.results = new LibpythoncljScriptTaskResultsPython(this);
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
    public LibpythoncljScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public LibpythoncljScriptTaskResultsPython getResults() {
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
    public static LibpythoncljScriptTaskEnginePython newInstance() {
        return new LibpythoncljScriptTaskEnginePython(InitializingPythonEngineWrapper.getInstance());
    }

}
