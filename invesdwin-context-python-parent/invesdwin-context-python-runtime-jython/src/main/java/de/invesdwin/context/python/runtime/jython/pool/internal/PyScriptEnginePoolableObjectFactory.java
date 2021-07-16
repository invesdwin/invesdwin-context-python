package de.invesdwin.context.python.runtime.jython.pool.internal;

import java.io.OutputStreamWriter;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;
import javax.script.ScriptException;

import org.python.jsr223.PyScriptEngine;
import org.python.jsr223.PyScriptEngineFactory;
import org.springframework.beans.factory.FactoryBean;
import org.zeroturnaround.exec.stream.slf4j.Slf4jDebugOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jWarnOutputStream;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.concurrent.pool.IPoolableObjectFactory;

@ThreadSafe
@Named
public final class PyScriptEnginePoolableObjectFactory
        implements IPoolableObjectFactory<PyScriptEngine>, FactoryBean<PyScriptEnginePoolableObjectFactory> {

    public static final PyScriptEnginePoolableObjectFactory INSTANCE = new PyScriptEnginePoolableObjectFactory();
    private static final PyScriptEngineFactory FACTORY = new PyScriptEngineFactory();

    private PyScriptEnginePoolableObjectFactory() {}

    @Override
    public PyScriptEngine makeObject() {
        final PyScriptEngine engine = (PyScriptEngine) FACTORY.getScriptEngine();
        engine.getContext().setWriter(new OutputStreamWriter(new Slf4jDebugOutputStream(IScriptTaskRunnerPython.LOG)));
        engine.getContext()
                .setErrorWriter(new OutputStreamWriter(new Slf4jWarnOutputStream(IScriptTaskRunnerPython.LOG)));
        return engine;
    }

    @Override
    public void destroyObject(final PyScriptEngine obj) {
        obj.close();
    }

    @Override
    public boolean validateObject(final PyScriptEngine obj) {
        return true;
    }

    @Override
    public void activateObject(final PyScriptEngine obj) {}

    /**
     * https://github.com/mrj0/jep/wiki/Performance-Considerations
     * 
     * https://github.com/spyder-ide/spyder/issues/2563
     * 
     * http://stackoverflow.com/questions/3543833/how-do-i-clear-all-variables-in-the-middle-of-a-python-script
     */
    @Override
    public void passivateObject(final PyScriptEngine obj) {
        try {
            obj.eval(IScriptTaskRunnerPython.CLEANUP_SCRIPT);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PyScriptEnginePoolableObjectFactory getObject() {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return PyScriptEnginePoolableObjectFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
