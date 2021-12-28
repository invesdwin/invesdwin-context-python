package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskEnginePython;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.math.Booleans;
import io.netty.util.concurrent.FastThreadLocal;

@NotThreadSafe
public final class PythonEngineWrapper {

    public static final PythonEngineWrapper INSTANCE = new PythonEngineWrapper();
    private static final FastThreadLocal<Boolean> INITIALIZING = new FastThreadLocal<>();

    private Map<Object, Object> globals;
    private boolean initialized = false;

    private PythonEngineWrapper() {
    }

    @SuppressWarnings("unchecked")
    private void maybeInit() {
        if (initialized) {
            return;
        }
        synchronized (this) {
            if (initialized) {
                return;
            }
            if (Booleans.isTrue(INITIALIZING.get())) {
                return;
            }
            INITIALIZING.set(Boolean.TRUE);
            final Map<String, Object> initParams = new HashMap<>();
            initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
            libpython_clj2.java_api.initialize(initParams);
            final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(this);
            engine.eval(new ClassPathResource(PythonEngineWrapper.class.getSimpleName() + ".py",
                    PythonEngineWrapper.class));
            engine.close();
            final Map<?, ?> mainModule = libpython_clj2.java_api.runString("");
            this.globals = (Map<Object, Object>) mainModule.get("globals");
            initialized = true;
            INITIALIZING.remove();
        }
    }

    public ILock getLock() {
        maybeInit();
        return GilLock.INSTANCE.getThreadLocalLock();
    }

    public void exec(final String expression) {
        maybeInit();
        IScriptTaskRunnerPython.LOG.debug("exec %s", expression);
        libpython_clj2.java_api.runString(expression);
    }

    public Object get(final String variable) {
        maybeInit();
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        libpython_clj2.java_api.runString("__ans__ = " + variable);
        return globals.get("__ans__");
    }

    public void set(final String variable, final Object value) {
        maybeInit();
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        globals.put(variable, value);
    }

}