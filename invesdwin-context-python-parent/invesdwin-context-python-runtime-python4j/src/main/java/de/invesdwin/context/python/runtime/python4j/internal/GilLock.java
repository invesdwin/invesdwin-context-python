package de.invesdwin.context.python.runtime.python4j.internal;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.mutable.MutableInt;
import org.nd4j.python4j.PythonGIL;

import de.invesdwin.util.concurrent.lock.ASimpleLock;
import de.invesdwin.util.concurrent.lock.IReentrantLock;
import de.invesdwin.util.concurrent.lock.Locks;

/**
 * WARNING: Don't share instances of this class between threads, or else deadlocks or jvm crashes might occur due to GIL
 * lock mismanagement.
 */
@NotThreadSafe
public final class GilLock extends ASimpleLock {

    private static final IReentrantLock MAIN_LOCK = Locks.newReentrantLock("python4j_MAIN");
    private static final String GIL_LOCK_NAME = "python4j_GIL";

    private final MutableInt lockedCount = new MutableInt();
    private PythonGIL key;

    public GilLock() {
    }

    @Override
    public void lock() {
        if (lockedCount.incrementAndGet() == 1) {
            MAIN_LOCK.lock();
            key = PythonGIL.lock();
        }
    }

    @Override
    public void unlock() {
        if (lockedCount.decrementAndGet() == 0) {
            key.close();
            key = null;
            MAIN_LOCK.unlock();
        }
    }

    @Override
    public String getName() {
        return GIL_LOCK_NAME;
    }

}
