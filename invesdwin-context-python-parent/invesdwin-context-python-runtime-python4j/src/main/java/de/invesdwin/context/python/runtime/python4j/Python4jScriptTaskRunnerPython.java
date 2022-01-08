package de.invesdwin.context.python.runtime.python4j;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.python4j.internal.IPythonEngineWrapper;
import de.invesdwin.context.python.runtime.python4j.internal.InitializingPythonEngineWrapper;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.error.Throwables;

/**
 * We have to always use the same thread for accessing the jep instance, thus run the tasks in an executor.
 * 
 * @author subes
 *
 */
@Immutable
@Named
public final class Python4jScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<Python4jScriptTaskRunnerPython> {

    public static final Python4jScriptTaskRunnerPython INSTANCE = new Python4jScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public Python4jScriptTaskRunnerPython() {
    }

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final IPythonEngineWrapper pythonEngine = InitializingPythonEngineWrapper.getInstance();
        //inputs
        final Python4jScriptTaskEnginePython engine = new Python4jScriptTaskEnginePython(pythonEngine);
        final ILock lock = engine.getSharedLock();
        lock.lock();
        try {
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
            lock.unlock();
        }
    }

    @Override
    public Python4jScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return Python4jScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
