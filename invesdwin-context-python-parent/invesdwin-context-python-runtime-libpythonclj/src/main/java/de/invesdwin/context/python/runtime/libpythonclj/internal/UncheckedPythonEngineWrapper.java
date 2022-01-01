package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties;
import de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljScriptTaskEnginePython;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.lang.UniqueNameGenerator;

@NotThreadSafe
public final class UncheckedPythonEngineWrapper implements IPythonEngineWrapper {

    public static final UncheckedPythonEngineWrapper INSTANCE = new UncheckedPythonEngineWrapper();

    private static final UniqueNameGenerator FUNCTIONS_NAMES = new UniqueNameGenerator();
    private Map<Object, Object> globals;
    private AutoCloseable fastCallable;

    private UncheckedPythonEngineWrapper() {
    }

    @SuppressWarnings("unchecked")
    public void init() {
        final Map<String, Object> initParams = new HashMap<>();
        initParams.put("python-executable", LibpythoncljProperties.PYTHON_COMMAND);
        libpython_clj2.java_api.initialize(initParams);

        final String fastCallableName = "__fastCallable__";
        final Map<?, ?> mainModule = libpython_clj2.java_api
                .runString("def " + fastCallableName + "(script):\n\texec(script)");
        this.globals = (Map<Object, Object>) mainModule.get("globals");
        this.fastCallable = libpython_clj2.java_api.makeFastcallable(globals.get(fastCallableName));

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
        fastEval(expression);
    }

    private void fastEval(final String expression) {
        if (expression.length() > 100) {
            libpython_clj2.java_api.runString(expression);
        } else {
            globals.put("__script__", expression);
            libpython_clj2.java_api.call(fastCallable, "__script__");
        }
    }

    @Override
    public Object get(final String variable) {
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        fastEval("__ans__ = " + variable);
        return globals.get("__ans__");
    }

    @Override
    public void set(final String variable, final Object value) {
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        globals.put(variable, value);
    }

}