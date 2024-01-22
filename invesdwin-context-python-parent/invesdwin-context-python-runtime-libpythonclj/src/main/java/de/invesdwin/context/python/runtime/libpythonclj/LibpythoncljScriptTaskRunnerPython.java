package de.invesdwin.context.python.runtime.libpythonclj;

import javax.annotation.concurrent.Immutable;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.LoggingDelegateScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.contract.callback.socket.SocketScriptTaskCallbackContext;
import de.invesdwin.context.python.runtime.libpythonclj.internal.IPythonEngineWrapper;
import de.invesdwin.context.python.runtime.libpythonclj.internal.InitializingPythonEngineWrapper;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.error.Throwables;
import jakarta.inject.Named;

/**
 * We have to always use the same thread for accessing the jep instance, thus run the tasks in an executor.
 * 
 * @author subes
 *
 */
@Immutable
@Named
public final class LibpythoncljScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<LibpythoncljScriptTaskRunnerPython> {

    public static final LibpythoncljScriptTaskRunnerPython INSTANCE = new LibpythoncljScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public LibpythoncljScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final IPythonEngineWrapper pythonEngine = InitializingPythonEngineWrapper.getInstance();
        //inputs
        final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(pythonEngine);
        final IScriptTaskCallback callback = scriptTask.getCallback();
        final SocketScriptTaskCallbackContext context;
        if (callback != null) {
            context = new SocketScriptTaskCallbackContext(LoggingDelegateScriptTaskCallback.maybeWrap(LOG, callback));
        } else {
            context = null;
        }
        final ILock lock = engine.getSharedLock();
        lock.lock();
        try {
            if (context != null) {
                context.init(engine);
            }
            scriptTask.populateInputs(engine.getInputs());

            //execute
            scriptTask.executeScript(engine);

            //results
            final T result = scriptTask.extractResults(engine.getResults());
            if (context != null) {
                context.deinit(engine);
            }
            engine.close();

            //return
            return result;
        } catch (final Throwable t) {
            throw Throwables.propagate(t);
        } finally {
            lock.unlock();
            if (context != null) {
                context.close();
            }
        }
    }

    @Override
    public LibpythoncljScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return LibpythoncljScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
