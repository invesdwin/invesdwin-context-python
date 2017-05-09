package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import com.github.rcaller.rstuff.RCaller;

import de.invesdwin.context.python.runtime.py4j.pool.RCallerObjectPool;
import de.invesdwin.context.r.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.error.Throwables;

@Immutable
@Named
public final class Py4jScriptTaskRunnerPython
        implements IScriptTaskRunnerPython, FactoryBean<Py4jScriptTaskRunnerPython> {

    public static final Py4jScriptTaskRunnerPython INSTANCE = new Py4jScriptTaskRunnerPython();

    public static final String INTERNAL_RESULT_VARIABLE = Py4jScriptTaskRunnerPython.class.getSimpleName() + "_result";

    /**
     * public for ServiceLoader support
     */
    public Py4jScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskPython<T> scriptTask) {
        //get session
        final RCaller rcaller;
        try {
            rcaller = RCallerObjectPool.INSTANCE.borrowObject();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        try {
            //inputs
            rcaller.getRCode().clearOnline();
            final Py4jScriptTaskInputsPython inputs = new Py4jScriptTaskInputsPython(rcaller);
            scriptTask.populateInputs(inputs);
            inputs.close();

            //execute
            rcaller.getRCode().addRCode(scriptTask.getScriptResourceAsString());
            rcaller.getRCode().addRCode(INTERNAL_RESULT_VARIABLE + " <- c()");
            rcaller.runAndReturnResultOnline(INTERNAL_RESULT_VARIABLE);

            //results
            final Py4jScriptTaskResultsPython results = new Py4jScriptTaskResultsPython(rcaller);
            final T result = scriptTask.extractResults(results);
            results.close();

            //return
            RCallerObjectPool.INSTANCE.returnObject(rcaller);
            return result;
        } catch (final Throwable t) {
            try {
                RCallerObjectPool.INSTANCE.invalidateObject(rcaller);
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
