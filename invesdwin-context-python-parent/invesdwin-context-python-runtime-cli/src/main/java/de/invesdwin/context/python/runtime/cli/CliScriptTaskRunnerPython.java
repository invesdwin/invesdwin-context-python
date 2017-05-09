package de.invesdwin.context.python.runtime.cli;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import com.github.rcaller.rstuff.RCaller;

import de.invesdwin.context.python.runtime.cli.pool.RCallerObjectPool;
import de.invesdwin.context.r.runtime.contract.AScriptTaskR;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerR;
import de.invesdwin.util.error.Throwables;

@Immutable
@Named
public final class CliScriptTaskRunnerPython implements IScriptTaskRunnerR, FactoryBean<CliScriptTaskRunnerPython> {

    public static final CliScriptTaskRunnerPython INSTANCE = new CliScriptTaskRunnerPython();

    public static final String INTERNAL_RESULT_VARIABLE = CliScriptTaskRunnerPython.class.getSimpleName() + "_result";

    /**
     * public for ServiceLoader support
     */
    public CliScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskR<T> scriptTask) {
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
            final CliScriptTaskInputsPython inputs = new CliScriptTaskInputsPython(rcaller);
            scriptTask.populateInputs(inputs);
            inputs.close();

            //execute
            rcaller.getRCode().addRCode(scriptTask.getScriptResourceAsString());
            rcaller.getRCode().addRCode(INTERNAL_RESULT_VARIABLE + " <- c()");
            rcaller.runAndReturnResultOnline(INTERNAL_RESULT_VARIABLE);

            //results
            final CliScriptTaskResultsPython results = new CliScriptTaskResultsPython(rcaller);
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
    public CliScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return CliScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
