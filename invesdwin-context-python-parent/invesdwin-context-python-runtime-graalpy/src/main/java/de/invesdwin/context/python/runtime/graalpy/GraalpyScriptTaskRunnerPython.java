package de.invesdwin.context.python.runtime.graalpy;

import javax.annotation.concurrent.Immutable;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.graalvm.jsr223.PolyglotScriptEngine;
import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.LoggingDelegateScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.graalpy.pool.GraalpyScriptEngineObjectPool;
import de.invesdwin.util.error.Throwables;
import jakarta.inject.Named;

@Immutable
@Named
public final class GraalpyScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<GraalpyScriptTaskRunnerPython> {

    public static final String INTERNAL_RESULT_VARIABLE = GraalpyScriptTaskRunnerPython.class.getSimpleName()
            + "_result";
    public static final GraalpyScriptTaskRunnerPython INSTANCE = new GraalpyScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public GraalpyScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final PolyglotScriptEngine pyScriptEngine = GraalpyScriptEngineObjectPool.INSTANCE.borrowObject();
        final IScriptTaskCallback callback = scriptTask.getCallback();
        final GraalpyScriptTaskCallbackContext context;
        if (callback != null) {
            context = new GraalpyScriptTaskCallbackContext(LoggingDelegateScriptTaskCallback.maybeWrap(LOG, callback));
        } else {
            context = null;
        }
        try {
            //inputs
            final GraalpyScriptTaskEnginePython engine = new GraalpyScriptTaskEnginePython(pyScriptEngine);
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
            GraalpyScriptEngineObjectPool.INSTANCE.returnObject(pyScriptEngine);
            return result;
        } catch (final Throwable t) {
            GraalpyScriptEngineObjectPool.INSTANCE.invalidateObject(pyScriptEngine);
            throw Throwables.propagate(t);
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

    @Override
    public GraalpyScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return GraalpyScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
