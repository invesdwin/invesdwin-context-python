package de.invesdwin.context.python.runtime.py4j.pool.internal;

import java.io.Closeable;

public interface IPy4jInterpreter extends Closeable {

    Object get(final String variable);

    void eval(String expression);

    void put(final String variable, final Object value);

    @Override
    void close();

    void cleanup();

}
