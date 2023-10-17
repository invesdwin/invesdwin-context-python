package de.invesdwin.context.python.runtime.jep;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class JepScriptTaskCallbackContextReturn {
    private final boolean returnExpression;
    private final Object returnValue;

    public JepScriptTaskCallbackContextReturn(final boolean returnExpression, final Object returnValue) {
        this.returnExpression = returnExpression;
        this.returnValue = returnValue;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public boolean isReturnExpression() {
        return returnExpression;
    }
}