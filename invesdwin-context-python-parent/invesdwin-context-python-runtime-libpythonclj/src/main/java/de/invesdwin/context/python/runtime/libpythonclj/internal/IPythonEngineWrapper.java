package de.invesdwin.context.python.runtime.libpythonclj.internal;

import de.invesdwin.util.concurrent.lock.ILock;

public interface IPythonEngineWrapper {

    ILock getLock();

    void exec(String expression);

    Object get(String variable);

    void set(String variable, Object value);

}
