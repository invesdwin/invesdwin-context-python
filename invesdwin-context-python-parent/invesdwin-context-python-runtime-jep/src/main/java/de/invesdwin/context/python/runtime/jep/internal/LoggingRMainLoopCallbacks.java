package de.invesdwin.context.python.runtime.jep.internal;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerR;
import de.invesdwin.util.lang.Strings;

@ThreadSafe
public final class LoggingRMainLoopCallbacks implements RMainLoopCallbacks {

    public static final LoggingRMainLoopCallbacks INSTANCE = new LoggingRMainLoopCallbacks();

    @GuardedBy("this")
    private final StringBuilder message = new StringBuilder();
    @GuardedBy("this")
    private boolean error = false;
    @GuardedBy("this")
    private final StringBuilder errorMessage = new StringBuilder();

    private LoggingRMainLoopCallbacks() {}

    @Override
    public synchronized void rWriteConsole(final Rengine rengine, final String text, final int oType) {
        if (oType != 0) {
            error = true;
            errorMessage.append(text);
        } else {
            //might have been a simple warning message like loading package or so, since a normal message followed it
            errorMessage.setLength(0);
        }
        if (text.endsWith("\n")) {
            message.append(text);
            if (Strings.isNotBlank(message)) {
                if (error) {
                    IScriptTaskRunnerR.LOG.warn(String.valueOf(message).trim());
                } else {
                    IScriptTaskRunnerR.LOG.debug(String.valueOf(message).trim());
                }
            }
            message.setLength(0);
            error = false;
        } else {
            message.append(text);
        }
    }

    public synchronized String getErrorMessage() {
        return String.valueOf(errorMessage).trim();
    }

    public synchronized void reset() {
        rWriteConsole(null, "\n", 0); //flush existing output
        message.setLength(0);
        error = false;
        errorMessage.setLength(0);
    }

    @Override
    public void rShowMessage(final Rengine rengine, final String message) {
        IScriptTaskRunnerR.LOG.warn("rShowMessage: %s", message);
    }

    @Override
    public void rSaveHistory(final Rengine rengine, final String filename) {
        IScriptTaskRunnerR.LOG.warn("rSaveHistory: %s", filename);
    }

    @Override
    public String rReadConsole(final Rengine rengine, final String prompt, final int addToHistory) {
        IScriptTaskRunnerR.LOG.warn("rReadConsole: %s [%s]", prompt, addToHistory);
        return null;
    }

    @Override
    public void rLoadHistory(final Rengine rengine, final String filename) {
        IScriptTaskRunnerR.LOG.warn("rLoadHistory: %s", filename);
    }

    @Override
    public void rFlushConsole(final Rengine rengine) {
        //        IScriptTaskRunner.LOG.warn("rFlushConsole");
    }

    @Override
    public String rChooseFile(final Rengine rengine, final int newFile) {
        IScriptTaskRunnerR.LOG.warn("rChooseFile: %s", newFile);
        return null;
    }

    @Override
    public void rBusy(final Rengine rengine, final int which) {
        IScriptTaskRunnerR.LOG.warn("rBusy: %s", which);
    }
}