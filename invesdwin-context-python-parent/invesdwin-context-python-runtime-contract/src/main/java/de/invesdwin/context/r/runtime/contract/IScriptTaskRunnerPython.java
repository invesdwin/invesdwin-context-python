package de.invesdwin.context.r.runtime.contract;

import de.invesdwin.context.log.Log;

public interface IScriptTaskRunnerPython {

    Log LOG = new Log(IScriptTaskRunnerPython.class);

    <T> T run(AScriptTaskPython<T> scriptTask);

}
