package de.invesdwin.context.python.runtime.contract;

import de.invesdwin.context.integration.script.IScriptTaskInputs;

public interface IScriptTaskInputsPython extends IScriptTaskInputs {

    @Override
    default void putExpression(final String variable, final String expression) {
        getEngine().eval(variable + " = " + expression);
    }

    @Override
    default void putNull(final String variable) {
        putExpression(variable, "None");
    }

    @Override
    default void remove(final String variable) {
        getEngine().eval("del " + variable);
    }

}
