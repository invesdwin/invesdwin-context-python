package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.python.jsr223.PyScriptEngine;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.jython.pool.PyScriptEngineObjectPool;
import de.invesdwin.util.error.Throwables;

@Immutable
@Named
public final class JythonScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<JythonScriptTaskRunnerPython> {

    public static final String INTERNAL_RESULT_VARIABLE = JythonScriptTaskRunnerPython.class.getSimpleName()
            + "_result";
    public static final JythonScriptTaskRunnerPython INSTANCE = new JythonScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public JythonScriptTaskRunnerPython() {
    }

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final PyScriptEngine pyScriptEngine = PyScriptEngineObjectPool.INSTANCE.borrowObject();
        try {
            //inputs
            final JythonScriptTaskEnginePython engine = new JythonScriptTaskEnginePython(pyScriptEngine);
            scriptTask.populateInputs(engine.getInputs());

            //execute
            scriptTask.executeScript(engine);

            //results
            final T result = scriptTask.extractResults(engine.getResults());
            engine.close();

            //return
            PyScriptEngineObjectPool.INSTANCE.returnObject(pyScriptEngine);
            return result;
        } catch (final Throwable t) {
            PyScriptEngineObjectPool.INSTANCE.destroyObject(pyScriptEngine);
            throw Throwables.propagate(t);
        }
    }

    @Override
    public JythonScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return JythonScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
