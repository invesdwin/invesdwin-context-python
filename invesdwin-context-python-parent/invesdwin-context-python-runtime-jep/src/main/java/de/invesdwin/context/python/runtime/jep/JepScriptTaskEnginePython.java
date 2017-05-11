package de.invesdwin.context.python.runtime.jep;

import java.io.File;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.io.FileUtils;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerPython;
import jep.Jep;

@NotThreadSafe
public class JepScriptTaskEnginePython implements IScriptTaskEngine {

    private Jep jep;
    private final JepScriptTaskInputsPython inputs;
    private final JepScriptTaskResultsPython results;

    public JepScriptTaskEnginePython(final Jep jep) {
        this.jep = jep;
        this.inputs = new JepScriptTaskInputsPython(this);
        this.results = new JepScriptTaskResultsPython(this);
    }

    /**
     * https://github.com/mrj0/jep/issues/55
     */
    @Override
    public void eval(final String expression) {
        try {
            //TODO: try to find a better alternative here
            final File script = File.createTempFile("pythonScript", ".py");
            FileUtils.writeStringToFile(script, expression);
            jep.runScript(script.getAbsolutePath());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JepScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public JepScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        eval(IScriptTaskRunnerPython.CLEANUP_SCRIPT);
        jep = null;
    }

    @Override
    public Jep unwrap() {
        return jep;
    }

}
