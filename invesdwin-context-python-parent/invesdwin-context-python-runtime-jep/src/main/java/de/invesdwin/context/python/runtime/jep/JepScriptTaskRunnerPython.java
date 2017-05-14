package de.invesdwin.context.python.runtime.jep;

import java.io.Closeable;
import java.util.concurrent.Callable;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.instrument.DynamicInstrumentationReflections;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.Futures;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.error.Throwables;
import io.netty.util.concurrent.FastThreadLocal;
import jep.Jep;
import jep.JepConfig;
import jep.JepException;

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

    private static final FastThreadLocal<JepWrapper> JEP = new FastThreadLocal<JepWrapper>() {
        @Override
        protected JepWrapper initialValue() {
            return new JepWrapper();
        }

        @Override
        protected void onRemoval(final JepWrapper value) throws Exception {
            value.close();
        }
    };

    static {
        DynamicInstrumentationReflections.addPathToJavaLibraryPath(JepProperties.JEP_LIBRARY_PATH);
    }

    @GuardedBy("this.class")
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
                final Jep jep = JEP.get().getJep();
                try {
                    //inputs
                    final JepScriptTaskEnginePython engine = new JepScriptTaskEnginePython(jep);
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

    private static class JepWrapper implements Closeable {
        private final Jep jep;

        JepWrapper() {
            try {
                this.jep = new Jep(new JepConfig().setSharedModules(JepProperties.getSharedModules())
                        .setInteractive(false)
                        .setRedirectOutputStreams(true));
            } catch (final JepException e) {
                throw new RuntimeException(
                        "Maybe you are mixing the python version with a different one for which jep was compiled for?",
                        e);
            }
        }

        public Jep getJep() {
            return jep;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }

        @Override
        public void close() {
            jep.close();
        }
    }

}
