package de.invesdwin.context.r.runtime.contract;

import de.invesdwin.context.integration.script.IScriptTaskInputs;

public interface IScriptTaskInputsPython extends IScriptTaskInputs {

    @Override
    default void putExpression(final String variable, final String expression) {
        getEngine().eval(variable + " = " + expression);
    }

}
