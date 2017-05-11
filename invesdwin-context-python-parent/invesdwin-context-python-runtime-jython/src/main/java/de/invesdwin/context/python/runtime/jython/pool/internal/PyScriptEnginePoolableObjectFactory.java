package de.invesdwin.context.python.runtime.jython.pool.internal;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.python.jsr223.PyScriptEngine;
import org.python.jsr223.PyScriptEngineFactory;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.pool.IPoolableObjectFactory;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerPython;

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
        return engine;
    }

    @Override
    public void destroyObject(final PyScriptEngine obj) throws Exception {
        obj.close();
    }

    @Override
    public boolean validateObject(final PyScriptEngine obj) {
        return true;
    }

    @Override
    public void activateObject(final PyScriptEngine obj) throws Exception {}

    /**
     * https://github.com/mrj0/jep/wiki/Performance-Considerations
     * 
     * https://github.com/spyder-ide/spyder/issues/2563
     * 
     * http://stackoverflow.com/questions/3543833/how-do-i-clear-all-variables-in-the-middle-of-a-python-script
     */
    @Override
    public void passivateObject(final PyScriptEngine obj) throws Exception {
        obj.eval(IScriptTaskRunnerPython.CLEANUP_SCRIPT);
    }

    @Override
    public PyScriptEnginePoolableObjectFactory getObject() throws Exception {
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
