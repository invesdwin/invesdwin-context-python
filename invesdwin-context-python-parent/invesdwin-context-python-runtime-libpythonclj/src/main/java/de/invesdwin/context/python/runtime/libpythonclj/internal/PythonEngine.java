package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.util.concurrent.lock.IReentrantLock;
import de.invesdwin.util.concurrent.lock.Locks;

@NotThreadSafe
public final class PythonEngine {

    public static final PythonEngine INSTANCE = new PythonEngine();

    private final IReentrantLock lock;

    private PythonEngine() {
        this.lock = Locks.newReentrantLock(PythonEngine.class.getSimpleName() + "_lock");
        final Map<String, Object> initParams = new HashMap<>();
        libpython_clj2.java_api.initialize(initParams);
    }

    public IReentrantLock getLock() {
        return lock;
    }

}