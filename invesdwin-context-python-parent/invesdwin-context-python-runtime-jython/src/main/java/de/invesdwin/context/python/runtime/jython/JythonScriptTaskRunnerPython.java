package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.python.jsr223.PyScriptEngine;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.jython.pool.PyScriptEngineObjectPool;
import de.invesdwin.context.r.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.error.Throwables;

@Immutable
@Named
public final class JythonScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<JythonScriptTaskRunnerPython> {

    public static final JythonScriptTaskRunnerPython INSTANCE = new JythonScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public JythonScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final PyScriptEngine pyScriptEngine;
        try {
            pyScriptEngine = PyScriptEngineObjectPool.INSTANCE.borrowObject();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        try {
            //inputs
            final JythonScriptTaskInputsPython inputs = new JythonScriptTaskInputsPython(pyScriptEngine);
            scriptTask.populateInputs(inputs);
            inputs.close();

            //execute
            pyScriptEngine.eval(scriptTask.getScriptResourceAsString());

            //results
            final JythonScriptTaskResultsPython results = new JythonScriptTaskResultsPython(pyScriptEngine);
            final T result = scriptTask.extractResults(results);
            results.close();

            //return
            PyScriptEngineObjectPool.INSTANCE.returnObject(pyScriptEngine);
            return result;
        } catch (final Throwable t) {
            try {
                PyScriptEngineObjectPool.INSTANCE.invalidateObject(pyScriptEngine);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
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
