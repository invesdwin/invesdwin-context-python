package de.invesdwin.context.python.runtime.contract;

import de.invesdwin.context.log.Log;

public interface IScriptTaskRunnerPython {

    String CLEANUP_SCRIPT = "for var in list(globals()):\n" //
            + "    if var[0] == '_': continue\n" //
            + "    del globals()[var]\n";

    Log LOG = new Log(IScriptTaskRunnerPython.class);

    <T> T run(AScriptTaskPython<T> scriptTask);

}
