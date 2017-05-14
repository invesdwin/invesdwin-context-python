package de.invesdwin.context.python.runtime.py4j.pool.internal;

public interface IPy4jInterpreter {

    Object get(final String variable);

    void eval(String expression);

    void put(final String variable, final Object value);

    void cleanup();

}
