package de.invesdwin.context.python.runtime.graalpy.pool;

import java.io.OutputStreamWriter;

import javax.annotation.concurrent.ThreadSafe;
import javax.script.ScriptException;

import org.springframework.beans.factory.FactoryBean;
import org.zeroturnaround.exec.stream.slf4j.Slf4jDebugOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jWarnOutputStream;

import de.invesdwin.context.graalvm.jsr223.PolyglotScriptEngine;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.graalpy.jsr223.GraalpyScriptEngineFactory;
import de.invesdwin.util.concurrent.pool.timeout.ATimeoutObjectPool;
import de.invesdwin.util.time.date.FTimeUnit;
import de.invesdwin.util.time.duration.Duration;
import jakarta.inject.Named;

@ThreadSafe
@Named
public final class GraalpyScriptEngineObjectPool extends ATimeoutObjectPool<PolyglotScriptEngine>
        implements FactoryBean<GraalpyScriptEngineObjectPool> {

    public static final GraalpyScriptEngineObjectPool INSTANCE = new GraalpyScriptEngineObjectPool();
    private static final GraalpyScriptEngineFactory FACTORY = new GraalpyScriptEngineFactory();

    private GraalpyScriptEngineObjectPool() {
        super(Duration.ONE_MINUTE, new Duration(10, FTimeUnit.SECONDS));
    }

    @Override
    public void invalidateObject(final PolyglotScriptEngine element) {
        element.close();
    }

    @Override
    protected PolyglotScriptEngine newObject() {
        final PolyglotScriptEngine engine = (PolyglotScriptEngine) FACTORY.getScriptEngine();
        engine.getContext().setWriter(new OutputStreamWriter(new Slf4jDebugOutputStream(IScriptTaskRunnerPython.LOG)));
        engine.getContext()
                .setErrorWriter(new OutputStreamWriter(new Slf4jWarnOutputStream(IScriptTaskRunnerPython.LOG)));
        return engine;
    }

    /**
     * https://github.com/mrj0/jep/wiki/Performance-Considerations
     * 
     * https://github.com/spyder-ide/spyder/issues/2563
     * 
     * http://stackoverflow.com/questions/3543833/how-do-i-clear-all-variables-in-the-middle-of-a-python-script
     */
    @Override
    protected boolean passivateObject(final PolyglotScriptEngine element) {
        try {
            element.eval(IScriptTaskRunnerPython.CLEANUP_SCRIPT);
            return true;
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GraalpyScriptEngineObjectPool getObject() {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return GraalpyScriptEngineObjectPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
