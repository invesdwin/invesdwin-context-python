package de.invesdwin.context.python.runtime.jep;

import java.util.concurrent.Callable;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.LoggingDelegateScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.jep.internal.JepWrapper;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.future.Futures;
import de.invesdwin.util.error.Throwables;
import jakarta.inject.Named;
import jep.Jep;

/**
 * We have to always use the same thread for accessing the jep instance, thus run the tasks in an executor.
 * 
 * @author subes
 *
 */
@Immutable
@Named
public final class JepScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<JepScriptTaskRunnerPython> {

    public static final JepScriptTaskRunnerPython INSTANCE = new JepScriptTaskRunnerPython();

    @GuardedBy("JepScriptTaskRunnerPython.class")
    private static WrappedExecutorService executor;

    /**
     * public for ServiceLoader support
     */
    public JepScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        final Callable<T> callable = new Callable<T>() {
            @Override
            public T call() throws Exception {
                //get session
                final Jep jep = JepWrapper.get().getJep();
                final JepScriptTaskCallbackContext context;
                final IScriptTaskCallback callback = scriptTask.getCallback();
                if (callback != null) {
                    context = new JepScriptTaskCallbackContext(
                            LoggingDelegateScriptTaskCallback.maybeWrap(LOG, callback));
                } else {
                    context = null;
                }
                try {
                    //inputs
                    final JepScriptTaskEnginePython engine = new JepScriptTaskEnginePython(jep);
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
                    return result;
                } catch (final Throwable t) {
                    throw Throwables.propagate(t);
                } finally {
                    if (context != null) {
                        context.close();
                    }
                }
            }
        };
        try {
            return Futures.submitAndGet(getExecutor(), callable);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JepScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return JepScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static synchronized WrappedExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(JepScriptTaskRunnerPython.class.getSimpleName() + "_jep",
                    JepProperties.THREAD_POOL_COUNT);
        }
        return executor;
    }

    public static synchronized void resetExecutor() {
        executor.shutdown();
        try {
            executor.awaitTermination();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor = null;
    }

}
