package de.invesdwin.context.python.runtime.jython.pool.internal;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.math.R.Rsession;

import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerR;
import de.invesdwin.util.lang.Reflections;
import de.invesdwin.util.lang.Strings;

@ThreadSafe
public final class RsessionLogger implements org.math.R.Logger {

    @GuardedBy("this")
    private final StringBuilder errorMessage = new StringBuilder();

    public RsessionLogger() {}

    @Override
    public synchronized void println(final String text, final Level level) {
        if (Strings.isNotBlank(text)) {
            switch (level) {
            case OUTPUT:
                IScriptTaskRunnerR.LOG.debug(text);
                errorMessage.setLength(0);
            case INFO:
                IScriptTaskRunnerR.LOG.trace(text);
                errorMessage.setLength(0);
                break;
            case WARNING:
                IScriptTaskRunnerR.LOG.warn(text);
                errorMessage.append(text);
                errorMessage.append("\n");
                break;
            case ERROR:
                IScriptTaskRunnerR.LOG.error(text);
                errorMessage.append(text);
                errorMessage.append("\n");
                break;
            default:
                IScriptTaskRunnerR.LOG.trace(text);
                errorMessage.setLength(0);
                break;
            }
        }
    }

    public synchronized String getErrorMessage() {
        return String.valueOf(errorMessage).trim();
    }

    @Override
    public synchronized void close() {
        errorMessage.setLength(0);
    }

    public static RsessionLogger get(final Rsession rsession) {
        return (RsessionLogger) Reflections.field("console").ofType(org.math.R.Logger.class).in(rsession).get();
    }

}
