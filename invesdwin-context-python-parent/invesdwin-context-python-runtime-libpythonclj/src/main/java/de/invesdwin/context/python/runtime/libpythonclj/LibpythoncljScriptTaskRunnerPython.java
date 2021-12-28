package de.invesdwin.context.python.runtime.libpythonclj;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.libpythonclj.internal.PythonEngine;
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
public final class LibpythoncljScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<LibpythoncljScriptTaskRunnerPython> {

    public static final LibpythoncljScriptTaskRunnerPython INSTANCE = new LibpythoncljScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public LibpythoncljScriptTaskRunnerPython() {
    }

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final PythonEngine pythonEngine = PythonEngine.INSTANCE;
        //inputs
        final LibpythoncljScriptTaskEnginePython engine = new LibpythoncljScriptTaskEnginePython(pythonEngine);
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
