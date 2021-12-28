package de.invesdwin.context.python.runtime.libpythonclj.internal;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.mutable.MutableInt;

import de.invesdwin.util.concurrent.lock.ASimpleLock;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.Locks;
import io.netty.util.concurrent.FastThreadLocal;

@ThreadSafe
public final class GilLock extends ASimpleLock {

    public static final GilLock INSTANCE = new GilLock();
    private static final ILock MAIN_LOCK = Locks.newReentrantLock("libpython-clj_MAIN");
    private static final String GIL_LOCK_NAME = "libpython-clj_GIL";

    private static final FastThreadLocal<ThreadLocalGilLock> STATE_HOLDER = new FastThreadLocal<ThreadLocalGilLock>() {
        @Override
        protected ThreadLocalGilLock initialValue() throws Exception {
            return new ThreadLocalGilLock();
        }
    };

    private GilLock() {
    }

    public ILock getThreadLocalLock() {
        return STATE_HOLDER.get();
    }

    @Override
    public void lock() {
        final ThreadLocalGilLock state = STATE_HOLDER.get();
        state.lock();
    }

    @Override
    public void unlock() {
        final ThreadLocalGilLock state = STATE_HOLDER.get();
        state.unlock();
    }

    @Override
    public String getName() {
        return GIL_LOCK_NAME;
    }

    private static class ThreadLocalGilLock extends ASimpleLock {
        private final MutableInt lockedCount = new MutableInt();
        private int key;

        @Override
        public String getName() {
            return GIL_LOCK_NAME;
        }

        @Override
        public void lock() {
            MAIN_LOCK.lock();
            if (lockedCount.incrementAndGet() == 1) {
                key = libpython_clj2.java_api.lockGIL();
            }
        }

        @Override
        public void unlock() {
            if (lockedCount.decrementAndGet() == 0) {
                libpython_clj2.java_api.unlockGIL(key);
            }
            MAIN_LOCK.unlock();
        }
    }

}
