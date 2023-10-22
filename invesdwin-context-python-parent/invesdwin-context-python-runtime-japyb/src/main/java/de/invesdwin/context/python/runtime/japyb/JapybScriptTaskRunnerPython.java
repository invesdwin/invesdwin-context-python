package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.Immutable;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.contract.callback.socket.SocketScriptTaskCallbackContext;
import de.invesdwin.context.python.runtime.japyb.pool.ExtendedPythonBridge;
import de.invesdwin.context.python.runtime.japyb.pool.JapybObjectPool;
import de.invesdwin.util.error.Throwables;
import jakarta.inject.Named;

@Immutable
@Named
public final class JapybScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<JapybScriptTaskRunnerPython> {

    public static final JapybScriptTaskRunnerPython INSTANCE = new JapybScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public JapybScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final ExtendedPythonBridge pythonBridge = JapybObjectPool.INSTANCE.borrowObject();
        final IScriptTaskCallback callback = scriptTask.getCallback();
        final SocketScriptTaskCallbackContext context;
        if (callback != null) {
            context = new SocketScriptTaskCallbackContext(callback);
        } else {
            context = null;
        }
        try {
            //inputs
            final JapybScriptTaskEnginePython engine = new JapybScriptTaskEnginePython(pythonBridge);
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
            JapybObjectPool.INSTANCE.returnObject(pythonBridge);
            return result;
        } catch (final Throwable t) {
            //we have to destroy instances on exceptions, otherwise e.g. SFrontiers.jl might get stuck with some inconsistent state
            JapybObjectPool.INSTANCE.invalidateObject(pythonBridge);
            throw Throwables.propagate(t);
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

    @Override
    public JapybScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return JapybScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
