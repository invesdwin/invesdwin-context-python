package de.invesdwin.context.python.runtime.japyb.pool;

import java.io.IOException;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.japyb.JapybScriptTaskEnginePython;

@NotThreadSafe
public class ExtendedPythonBridge extends ModifiedPythonBridge {

    private final PythonResetContext resetContext;

    public ExtendedPythonBridge() {
        super();
        this.resetContext = new PythonResetContext(new JapybScriptTaskEnginePython(this));
    }

    @Override
    public void open() throws IOException {
        super.open();
        resetContext.init();
    }

    public void reset() throws IOException {
        getErrWatcher().clearLog();
        resetContext.reset();
        getErrWatcher().clearLog();
    }

}
