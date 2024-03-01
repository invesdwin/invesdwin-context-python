package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.japyb.pool.ExtendedPythonBridge;
import de.invesdwin.context.python.runtime.japyb.pool.JapybObjectPool;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;

@NotThreadSafe
public class JapybScriptTaskEnginePython implements IScriptTaskEngine {

    private ExtendedPythonBridge bridge;
    private final JapybScriptTaskInputsPython inputs;
    private final JapybScriptTaskResultsPython results;

    public JapybScriptTaskEnginePython(final ExtendedPythonBridge bridge) {
        this.bridge = bridge;
        this.inputs = new JapybScriptTaskInputsPython(this);
        this.results = new JapybScriptTaskResultsPython(this);
    }

    @Override
    public void eval(final String expression) {
        bridge.eval(expression);
    }

    @Override
    public JapybScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public JapybScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        bridge = null;
    }

    @Override
    public ExtendedPythonBridge unwrap() {
        return bridge;
    }

    /**
     * Each instance has its own engine, so no shared locking required.
     */
    @Override
    public ILock getSharedLock() {
        return DisabledLock.INSTANCE;
    }

    /**
     * No executor needed.
     */
    @Override
    public WrappedExecutorService getSharedExecutor() {
        return null;
    }

    public static JapybScriptTaskEnginePython newInstance() {
        return new JapybScriptTaskEnginePython(JapybObjectPool.INSTANCE.borrowObject()) {
            @Override
            public void close() {
                final ExtendedPythonBridge unwrap = unwrap();
                if (unwrap != null) {
                    JapybObjectPool.INSTANCE.returnObject(unwrap);
                }
                super.close();
            }
        };
    }

}
