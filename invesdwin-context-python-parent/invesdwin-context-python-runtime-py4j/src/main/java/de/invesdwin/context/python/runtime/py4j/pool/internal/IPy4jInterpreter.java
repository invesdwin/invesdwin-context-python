package de.invesdwin.context.python.runtime.py4j.pool.internal;

public interface IPy4jInterpreter {

    Object get(String variable);

    void eval(String expression);

    void put(String variable, Object value);

    void cleanup();

}
