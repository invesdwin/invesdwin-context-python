package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskEnginePython;
import de.invesdwin.util.concurrent.lock.ILock;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * WARNING: Don't share instances of this class between threads, or else deadlocks or jvm crashes might occur due to GIL
 * lock mismanagement.
 */
@NotThreadSafe
public final class UncheckedPythonEngineWrapper implements IPythonEngineWrapper {

    private static final FastThreadLocal<UncheckedPythonEngineWrapper> INSTANCE = new FastThreadLocal<UncheckedPythonEngineWrapper>() {
        @Override
        protected UncheckedPythonEngineWrapper initialValue() throws Exception {
            return new UncheckedPythonEngineWrapper();
        }
    };

    private final GilLock gilLock = new GilLock();;

    private UncheckedPythonEngineWrapper() {
    }

    public void init() {
        synchronized (UncheckedPythonEngineWrapper.class) {
            final Map<String, Object> initParams = new HashMap<>();
            initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
            libpython_clj2.java_api.initialize(initParams);

            gilLock.lock();
            try {
                final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(this);
                engine.eval(new ClassPathResource(UncheckedPythonEngineWrapper.class.getSimpleName() + ".py",
                        UncheckedPythonEngineWrapper.class));
                engine.close();
            } finally {
                gilLock.unlock();
            }
        }
    }

    @Override
    public ILock getLock() {
        return gilLock;
    }

    @Override
    public void exec(final String expression) {
        IScriptTaskRunnerPython.LOG.debug("exec %s", expression);
        eval(expression);
    }

    private void eval(final String expression) {
        gilLock.lock();
        try {
            libpython_clj2.java_api.runStringAsFile(expression);
        } finally {
            gilLock.unlock();
        }
    }

    @Override
    public Object get(final String variable) {
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        gilLock.lock();
        try {
            return libpython_clj2.java_api.runStringAsInput(variable);
        } finally {
            gilLock.unlock();
        }
    }

    @Override
    public void set(final String variable, final Object value) {
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        gilLock.lock();
        try {
            libpython_clj2.java_api.setGlobal(variable, value);
        } catch (final Throwable t) {
            throw new RuntimeException("Variable=" + variable + " Value=" + value, t);
        } finally {
            gilLock.unlock();
        }
    }

    public static UncheckedPythonEngineWrapper getInstance() {
        return UncheckedPythonEngineWrapper.INSTANCE.get();
    }

}