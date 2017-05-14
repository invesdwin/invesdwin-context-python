package de.invesdwin.context.python.runtime.py4j.pool.internal;

import java.io.Closeable;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class Py4jInterpreter implements Closeable {

    public Object eval(final String expression) {
        return null;
    }

    public void put(final String variable, final Object value) {}

    @Override
    public void close() {}
}
