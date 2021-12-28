package de.invesdwin.context.python.runtime.libpythonclj;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.libpythonclj.internal.PythonEngine;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;

@NotThreadSafe
public class LibpythoncljScriptTaskEnginePython implements IScriptTaskEngine {

    private PythonEngine pythonEngine;
    private final LibpythoncljScriptTaskInputsPython inputs;
    private final LibpythoncljScriptTaskResultsPython results;

    public LibpythoncljScriptTaskEnginePython(final PythonEngine pythonEngine) {
        this.pythonEngine = pythonEngine;
        this.inputs = new LibpythoncljScriptTaskInputsPython(this);
        this.results = new LibpythoncljScriptTaskResultsPython(this);
    }

    /**
     * https://github.com/mrj0/jep/issues/55
     */
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
    public PythonEngine unwrap() {
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

    public static LibpythoncljScriptTaskEnginePython newInstance() {
        return new LibpythoncljScriptTaskEnginePython(PythonEngine.INSTANCE);
    }

}
