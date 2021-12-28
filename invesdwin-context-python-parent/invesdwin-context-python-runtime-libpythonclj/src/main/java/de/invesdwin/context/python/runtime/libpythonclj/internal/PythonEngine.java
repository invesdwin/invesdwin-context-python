package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskEnginePython;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.IReentrantLock;
import de.invesdwin.util.concurrent.lock.Locks;

@NotThreadSafe
public final class PythonEngine {

    public static final PythonEngine INSTANCE = new PythonEngine();

    private final IReentrantLock lock;
    private final Map<Object, Object> globals;

    private PythonEngine() {
        this.lock = Locks.newReentrantLock(PythonEngine.class.getSimpleName() + "_lock");
        final Map<String, Object> initParams = new HashMap<>();
        initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
        libpython_clj2.java_api.initialize(initParams);
        final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(this);
        engine.eval(new ClassPathResource(PythonEngine.class.getSimpleName() + ".py", PythonEngine.class));
        engine.close();
        this.globals = getGlobals();
    }

    @SuppressWarnings("unchecked")
    private Map<Object, Object> getGlobals() {
        final Map<?, ?> mainModule = libpython_clj2.java_api.runString("");
        return (Map<Object, Object>) mainModule.get("globals");
    }

    public ILock getLock() {
        return GilLock.INSTANCE.getThreadLocalLock();
    }

    public void exec(final String expression) {
        IScriptTaskRunnerPython.LOG.debug("exec %s", expression);
        libpython_clj2.java_api.runString(expression);
    }

    public Object get(final String variable) {
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        libpython_clj2.java_api.runString("__ans__ = " + variable);
        return globals.get("__ans__");
    }

    public void set(final String variable, final Object value) {
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        globals.put(variable, value);
    }

}