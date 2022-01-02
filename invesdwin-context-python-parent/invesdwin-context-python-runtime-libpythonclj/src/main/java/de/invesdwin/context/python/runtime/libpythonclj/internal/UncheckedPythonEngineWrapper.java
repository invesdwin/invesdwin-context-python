package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskEnginePython;
import de.invesdwin.util.concurrent.lock.ILock;

@NotThreadSafe
public final class UncheckedPythonEngineWrapper implements IPythonEngineWrapper {

    public static final UncheckedPythonEngineWrapper INSTANCE = new UncheckedPythonEngineWrapper();

    private UncheckedPythonEngineWrapper() {
    }

    public void init() {
        final Map<String, Object> initParams = new HashMap<>();
        initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
        libpython_clj2.java_api.initialize(initParams);

        final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(this);
        engine.eval(new ClassPathResource(UncheckedPythonEngineWrapper.class.getSimpleName() + ".py",
                UncheckedPythonEngineWrapper.class));
        engine.close();
    }

    @Override
    public ILock getLock() {
        return GilLock.INSTANCE.getThreadLocalLock();
    }

    @Override
    public void exec(final String expression) {
        IScriptTaskRunnerPython.LOG.debug("exec %s", expression);
        eval(expression);
    }

    private void eval(final String expression) {
        final ILock lock = getLock();
        lock.lock();
        try {
            libpython_clj2.java_api.runStringAsInput(expression);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Object get(final String variable) {
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        final ILock lock = getLock();
        lock.lock();
        try {
            return libpython_clj2.java_api.runStringAsInput(variable);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void set(final String variable, final Object value) {
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        final ILock lock = getLock();
        lock.lock();
        try {
            libpython_clj2.java_api.setGlobal(null, variable, value);
        } finally {
            lock.unlock();
        }
    }

}