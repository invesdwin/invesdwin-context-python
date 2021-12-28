package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskEnginePython;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskRunnerPython;
import de.invesdwin.util.concurrent.lock.IReentrantLock;
import de.invesdwin.util.concurrent.lock.Locks;

@NotThreadSafe
public final class PythonEngine {

    public static final PythonEngine INSTANCE = new PythonEngine();

    private final IReentrantLock lock;

    private PythonEngine() {
        this.lock = Locks.newReentrantLock(PythonEngine.class.getSimpleName() + "_lock");
        final Map<String, Object> initParams = new HashMap<>();
        initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
        libpython_clj2.java_api.initialize(initParams);
        final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(this);
        engine.eval(new ClassPathResource("LibpythoncljSetup.py", LibpythoncljScriptTaskRunnerPython.class));
        engine.close();
    }

    public IReentrantLock getLock() {
        return lock;
    }

    public void exec(final String expression) {
        final Map<?, ?> map = libpython_clj2.java_api.runString(expression);
        final Object globals = map.get("globals");
        System.out.println(globals);
    }

    public Object getValue(final String variable) {
        return libpython_clj2.java_api.getItem("globals", variable);
    }

    public void set(final String variable, final Object value) {
        libpython_clj2.java_api.setItem("globals", variable, value);
    }

}