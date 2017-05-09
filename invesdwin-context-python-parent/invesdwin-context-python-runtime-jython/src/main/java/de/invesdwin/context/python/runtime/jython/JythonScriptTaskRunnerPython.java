package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.Immutable;
import javax.inject.Named;

import org.math.R.Rsession;
import org.rosuda.REngine.REXP;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.jython.pool.RsessionObjectPool;
import de.invesdwin.context.r.runtime.contract.AScriptTaskR;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerR;
import de.invesdwin.util.error.Throwables;

@Immutable
@Named
public final class JythonScriptTaskRunnerPython implements IScriptTaskRunnerR, FactoryBean<JythonScriptTaskRunnerPython> {

    public static final JythonScriptTaskRunnerPython INSTANCE = new JythonScriptTaskRunnerPython();

    /**
     * public for ServiceLoader support
     */
    public JythonScriptTaskRunnerPython() {}

    @Override
    public <T> T run(final AScriptTaskR<T> scriptTask) {
        //get session
        final Rsession rsession;
        try {
            rsession = RsessionObjectPool.INSTANCE.borrowObject();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        try {
            //inputs
            final JythonScriptTaskInputsPython inputs = new JythonScriptTaskInputsPython(rsession);
            scriptTask.populateInputs(inputs);
            inputs.close();

            //execute
            eval(rsession, scriptTask.getScriptResourceAsString());

            //results
            final JythonScriptTaskResultsPython results = new JythonScriptTaskResultsPython(rsession);
            final T result = scriptTask.extractResults(results);
            results.close();

            //return
            RsessionObjectPool.INSTANCE.returnObject(rsession);
            return result;
        } catch (final Throwable t) {
            try {
                RsessionObjectPool.INSTANCE.invalidateObject(rsession);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
            throw Throwables.propagate(t);
        }
    }

    public static void eval(final Rsession rsession, final String expression) {
        final REXP eval = rsession.eval(expression);
        if (eval == null) {
            throw new IllegalStateException(
                    String.valueOf(de.invesdwin.context.python.runtime.jython.pool.internal.RsessionLogger.get(rsession)
                            .getErrorMessage()));
        }
    }

    @Override
    public JythonScriptTaskRunnerPython getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return JythonScriptTaskRunnerPython.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
