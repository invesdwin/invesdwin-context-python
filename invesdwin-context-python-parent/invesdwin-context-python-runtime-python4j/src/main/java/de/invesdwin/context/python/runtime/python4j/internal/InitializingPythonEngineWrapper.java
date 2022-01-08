package de.invesdwin.context.python.runtime.python4j.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.util.concurrent.lock.ILock;

@NotThreadSafe
public final class InitializingPythonEngineWrapper implements IPythonEngineWrapper {

    private static boolean initialized = false;
    private final IPythonEngineWrapper delegate;

    public InitializingPythonEngineWrapper() {
        this.delegate = InitializingPythonEngineWrapper.getInstance();
    }

    public static void maybeInit() {
        if (initialized) {
            return;
        }
        synchronized (InitializingPythonEngineWrapper.class) {
            if (initialized) {
                return;
            }
            UncheckedPythonEngineWrapper.getInstance().init();
            initialized = true;
        }
    }

    @Override
    public ILock getLock() {
        maybeInit();
        return delegate.getLock();
    }

    @Override
    public void exec(final String expression) {
        maybeInit();
        delegate.exec(expression);
    }

    @Override
    public Object get(final String variable) {
        maybeInit();
        return delegate.get(variable);
    }

    @Override
    public void set(final String variable, final Object value) {
        maybeInit();
        delegate.set(variable, value);
    }

    public static IPythonEngineWrapper getInstance() {
        InitializingPythonEngineWrapper.maybeInit();
        return UncheckedPythonEngineWrapper.getInstance();
    }

}