package de.invesdwin.context.python.runtime.libpythonclj.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;

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
    private final LoadingCache<String, AutoCloseable> fastCallableCache;

    private UncheckedPythonEngineWrapper() {
        fastCallableCache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .removalListener(this::fastCallableCache_onRemoval)
                .<String, AutoCloseable> build(this::fastCallableCache_load);
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
        fastEval(expression);
    }

    private void fastEval(final String expression) {
        if (expression.length() > 100) {
            libpython_clj2.java_api.runString(expression);
        } else {
            final AutoCloseable expr = fastCallableCache.get(expression);
            libpython_clj2.java_api.fastcall(expr, null);
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

    private void fastCallableCache_onRemoval(final String key, final AutoCloseable value, final RemovalCause cause) {
        try {
            value.close();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AutoCloseable fastCallableCache_load(final String key) {
        final StringBuilder sb = new StringBuilder("def ");
        sb.append(FUNCTIONS_NAMES.get("fastCallable"));
        sb.append("():");
        //        final String[] lines = Strings.splitPreserveAllTokens(Strings.normalizeNewlines(key), "\n");
        //        for (int i = 0; i < lines.length; i++) {
        //            sb.append("\n\t");
        //            sb.append(lines[i]);
        //        }
        sb.append("\n\treturn 1");
        return libpython_clj2.java_api.makeFastcallable(sb.toString());
    }

}