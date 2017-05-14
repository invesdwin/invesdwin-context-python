package de.invesdwin.context.python.runtime.py4j.pool.internal;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ReadyApplication {

    private final AtomicBoolean ready = new AtomicBoolean(false);

    public void ready() {
        ready.set(true);
    }

    public boolean isReady() {
        return ready.get();
    }

}
