package de.invesdwin.context.python.runtime.libpythonclj.internal;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.mutable.MutableInt;

import de.invesdwin.util.concurrent.lock.ASimpleLock;
import de.invesdwin.util.concurrent.lock.IReentrantLock;
import de.invesdwin.util.concurrent.lock.Locks;

@NotThreadSafe
public final class GilLock extends ASimpleLock {

    private static final IReentrantLock MAIN_LOCK = Locks.newReentrantLock("libpython-clj_MAIN");
    private static final String GIL_LOCK_NAME = "libpython-clj_GIL";

    private final MutableInt lockedCount = new MutableInt();
    private long key;

    public GilLock() {
    }

    @Override
    public void lock() {
        if (lockedCount.incrementAndGet() == 1) {
            MAIN_LOCK.lock();
            key = libpython_clj2.java_api.lockGIL();
        }
    }

    @Override
    public void unlock() {
        if (lockedCount.decrementAndGet() == 0) {
            libpython_clj2.java_api.unlockGIL(key);
            MAIN_LOCK.unlock();
        }
    }

    @Override
    public String getName() {
        return GIL_LOCK_NAME;
    }

}
