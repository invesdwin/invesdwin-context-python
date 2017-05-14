package de.invesdwin.context.python.runtime.contract;

import de.invesdwin.context.integration.script.IScriptTaskResults;

public interface IScriptTaskResultsPython extends IScriptTaskResults {

    @Override
    default boolean isDefined(final String variable) {
        return getBoolean("'" + variable + "' in locals() or '" + variable + "' in globals()");
    }

    @Override
    default boolean isNull(final String variable) {
        return getBoolean(variable + " is None");
    }

}
