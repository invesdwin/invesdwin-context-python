package de.invesdwin.context.r.runtime.contract;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.script.AScriptTask;

@NotThreadSafe
public abstract class AScriptTaskPython<V> extends AScriptTask<V, IScriptTaskRunnerPython> {

    @Override
    public V run(final IScriptTaskRunnerPython runner) {
        return runner.run(this);
    }

    public V run() {
        return run(ProvidedScriptTaskRunnerPython.INSTANCE);
    }

}
