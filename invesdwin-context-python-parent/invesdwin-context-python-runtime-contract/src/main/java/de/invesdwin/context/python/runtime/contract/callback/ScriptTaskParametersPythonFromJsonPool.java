package de.invesdwin.context.python.runtime.contract.callback;

import javax.annotation.concurrent.ThreadSafe;

import de.invesdwin.util.concurrent.pool.AAgronaObjectPool;

@ThreadSafe
public final class ScriptTaskParametersPythonFromJsonPool
        extends AAgronaObjectPool<ScriptTaskParametersPythonFromJson> {

    public static final ScriptTaskParametersPythonFromJsonPool INSTANCE = new ScriptTaskParametersPythonFromJsonPool();

    private ScriptTaskParametersPythonFromJsonPool() {}

    @Override
    protected ScriptTaskParametersPythonFromJson newObject() {
        return new ScriptTaskParametersPythonFromJson();
    }

    @Override
    protected boolean passivateObject(final ScriptTaskParametersPythonFromJson element) {
        element.close();
        return true;
    }

}
