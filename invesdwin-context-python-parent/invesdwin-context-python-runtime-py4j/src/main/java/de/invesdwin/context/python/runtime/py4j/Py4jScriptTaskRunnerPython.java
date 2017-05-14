package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.py4j.pool.Py4jInterpreterObjectPool;
import de.invesdwin.context.python.runtime.py4j.pool.internal.Py4jInterpreter;
import de.invesdwin.context.r.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.error.Throwables;

@Immutable
@Named
public final class Py4jScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<Py4jScriptTaskRunnerPython> {

    public static final String INTERNAL_RESULT_VARIABLE = Py4jScriptTaskRunnerPython.class.getSimpleName() + "_result";
    public static final Py4jScriptTaskRunnerPython INSTANCE = new Py4jScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public Py4jScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final Py4jInterpreter pyScriptEngine;
        try {
            pyScriptEngine = Py4jInterpreterObjectPool.INSTANCE.borrowObject();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        try {
            //inputs
            final Py4jScriptTaskEnginePython engine = new Py4jScriptTaskEnginePython(pyScriptEngine);
            scriptTask.populateInputs(engine.getInputs());

            //execute
            scriptTask.executeScript(engine);

            //results
            final T result = scriptTask.extractResults(engine.getResults());
            engine.close();

            //return
            Py4jInterpreterObjectPool.INSTANCE.returnObject(pyScriptEngine);
            return result;
        } catch (final Throwable t) {
            try {
                Py4jInterpreterObjectPool.INSTANCE.invalidateObject(pyScriptEngine);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
            throw Throwables.propagate(t);
        }
    }

    @Override
    public Py4jScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return Py4jScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
