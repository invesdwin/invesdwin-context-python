package de.invesdwin.context.python.runtime.jep;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.jep.internal.JepWrapper;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;
import jep.Jep;

@NotThreadSafe
public class JepScriptTaskEnginePython implements IScriptTaskEngine {

    private Jep jep;
    private final JepScriptTaskInputsPython inputs;
    private final JepScriptTaskResultsPython results;

    public JepScriptTaskEnginePython(final Jep jep) {
        this.jep = jep;
        this.inputs = new JepScriptTaskInputsPython(this);
        this.results = new JepScriptTaskResultsPython(this);
    }

    @Override
    public void eval(final String expression) {
        jep.exec(expression);
    }

    @Override
    public JepScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public JepScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        eval("restoreContext()");
        jep = null;
    }

    @Override
    public Jep unwrap() {
        return jep;
    }

    /**
     * Jep can only allows access within the same thread. Thus not lock needed. Though be careful about not trying to
     * access the instance from other threads. This will lead to exceptions.
     */
    @Override
    public ILock getSharedLock() {
        return DisabledLock.INSTANCE;
    }

    @Override
    public WrappedExecutorService getSharedExecutor() {
        return JepScriptTaskRunnerPython.getExecutor();
    }

    public static JepScriptTaskEnginePython newInstance() {
        return new JepScriptTaskEnginePython(JepWrapper.get().getJep());
    }

}
