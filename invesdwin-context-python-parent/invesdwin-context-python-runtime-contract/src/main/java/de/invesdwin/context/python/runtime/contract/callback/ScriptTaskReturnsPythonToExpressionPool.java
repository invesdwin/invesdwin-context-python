package de.invesdwin.context.python.runtime.contract.callback;

import javax.annotation.concurrent.ThreadSafe;

import de.invesdwin.util.concurrent.pool.AAgronaObjectPool;

@ThreadSafe
public final class ScriptTaskReturnsPythonToExpressionPool
        extends AAgronaObjectPool<ScriptTaskReturnsPythonToExpression> {

    public static final ScriptTaskReturnsPythonToExpressionPool INSTANCE = new ScriptTaskReturnsPythonToExpressionPool();

    private ScriptTaskReturnsPythonToExpressionPool() {}

    @Override
    protected ScriptTaskReturnsPythonToExpression newObject() {
        return new ScriptTaskReturnsPythonToExpression();
    }

    @Override
    protected boolean passivateObject(final ScriptTaskReturnsPythonToExpression element) {
        element.close();
        return true;
    }

}
