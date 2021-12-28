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

    private Map<Object, Object> globals;

    private UncheckedPythonEngineWrapper() {
    }

    @SuppressWarnings("unchecked")
    public void init() {
        final Map<String, Object> initParams = new HashMap<>();
        initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
        libpython_clj2.java_api.initialize(initParams);
        final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(this);
        engine.eval(new ClassPathResource(UncheckedPythonEngineWrapper.class.getSimpleName() + ".py",
                UncheckedPythonEngineWrapper.class));
        engine.close();
        final Map<?, ?> mainModule = libpython_clj2.java_api.runString("");
        this.globals = (Map<Object, Object>) mainModule.get("globals");
    }

    @Override
    public ILock getLock() {
        return GilLock.INSTANCE.getThreadLocalLock();
    }

    @Override
    public void exec(final String expression) {
        IScriptTaskRunnerPython.LOG.debug("exec %s", expression);
        libpython_clj2.java_api.runString(expression);
    }

    @Override
    public Object get(final String variable) {
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        libpython_clj2.java_api.runString("__ans__ = " + variable);
        return globals.get("__ans__");
    }

    @Override
    public void set(final String variable, final Object value) {
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        globals.put(variable, value);
    }

}