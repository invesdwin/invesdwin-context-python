package de.invesdwin.context.python.runtime.libpythonclj.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.util.concurrent.lock.ILock;

@NotThreadSafe
public final class InitializingPythonEngineWrapper implements IPythonEngineWrapper {

    public static final InitializingPythonEngineWrapper INSTANCE = new InitializingPythonEngineWrapper();
    private boolean initialized = false;

    private InitializingPythonEngineWrapper() {
    }

    public void maybeInit() {
        if (initialized) {
            return;
        }
        synchronized (this) {
            if (initialized) {
                return;
            }
            UncheckedPythonEngineWrapper.INSTANCE.init();
            initialized = true;
        }
    }

    @Override
    public ILock getLock() {
        maybeInit();
        return UncheckedPythonEngineWrapper.INSTANCE.getLock();
    }

    @Override
    public void exec(final String expression) {
        maybeInit();
        UncheckedPythonEngineWrapper.INSTANCE.exec(expression);
    }

    @Override
    public Object get(final String variable) {
        maybeInit();
        return UncheckedPythonEngineWrapper.INSTANCE.get(variable);
    }

    @Override
    public void set(final String variable, final Object value) {
        maybeInit();
        UncheckedPythonEngineWrapper.INSTANCE.set(variable, value);
    }

    public static IPythonEngineWrapper getInstance() {
        INSTANCE.maybeInit();
        return UncheckedPythonEngineWrapper.INSTANCE;
    }

}