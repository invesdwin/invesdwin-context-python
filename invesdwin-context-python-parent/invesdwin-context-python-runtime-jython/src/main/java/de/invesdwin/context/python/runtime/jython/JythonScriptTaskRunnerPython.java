package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.Immutable;

import org.python.jsr223.PyScriptEngine;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.LoggingDelegateScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.jython.pool.PyScriptEngineObjectPool;
import de.invesdwin.util.error.Throwables;
import jakarta.inject.Named;

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
    public JythonScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final PyScriptEngine pyScriptEngine = PyScriptEngineObjectPool.INSTANCE.borrowObject();
        final IScriptTaskCallback callback = scriptTask.getCallback();
        final JythonScriptTaskCallbackContext context;
        if (callback != null) {
            context = new JythonScriptTaskCallbackContext(LoggingDelegateScriptTaskCallback.maybeWrap(LOG, callback));
        } else {
            context = null;
        }
        try {
            //inputs
            final JythonScriptTaskEnginePython engine = new JythonScriptTaskEnginePython(pyScriptEngine);
            if (context != null) {
                context.init(engine);
            }
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
            PyScriptEngineObjectPool.INSTANCE.invalidateObject(pyScriptEngine);
            throw Throwables.propagate(t);
        } finally {
            if (context != null) {
                context.close();
            }
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
