package de.invesdwin.context.python.runtime.libpythonclj;

import java.util.concurrent.Callable;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.internal.PythonEngine;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.future.Futures;
import de.invesdwin.util.error.Throwables;

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

    @GuardedBy("JepScriptTaskRunnerPython.class")
    private static WrappedExecutorService executor;

    /**
     * public for ServiceLoader support
     */
    public LibpythoncljScriptTaskRunnerPython() {
    }

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        final Callable<T> callable = new Callable<T>() {
            @Override
            public T call() throws Exception {
                //get session
                final Jep jep = PythonEngine.get().getJep();
                try {
                    //inputs
                    final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(jep);
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

    public static synchronized WrappedExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(LibpythoncljScriptTaskRunnerPython.class.getSimpleName() + "_jep",
                    LibpythoncljProperties.THREAD_POOL_COUNT);
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
