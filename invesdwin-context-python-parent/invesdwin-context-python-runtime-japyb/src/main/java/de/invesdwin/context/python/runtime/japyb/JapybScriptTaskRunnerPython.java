package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.Immutable;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
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
        final ExtendedPythonBridge juliaCaller = JapybObjectPool.INSTANCE.borrowObject();
        try {
            //inputs
            final JapybScriptTaskEnginePython engine = new JapybScriptTaskEnginePython(juliaCaller);
            scriptTask.populateInputs(engine.getInputs());

            //execute
            scriptTask.executeScript(engine);

            //results
            final T result = scriptTask.extractResults(engine.getResults());
            engine.close();

            //return
            JapybObjectPool.INSTANCE.returnObject(juliaCaller);
            return result;
        } catch (final Throwable t) {
            //we have to destroy instances on exceptions, otherwise e.g. SFrontiers.jl might get stuck with some inconsistent state
            JapybObjectPool.INSTANCE.invalidateObject(juliaCaller);
            throw Throwables.propagate(t);
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
