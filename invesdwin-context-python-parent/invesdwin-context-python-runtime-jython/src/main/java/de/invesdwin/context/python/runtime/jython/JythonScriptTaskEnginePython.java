package de.invesdwin.context.python.runtime.jython;

import javax.annotation.concurrent.NotThreadSafe;
import javax.script.ScriptException;

import org.python.jsr223.PyScriptEngine;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.jython.pool.PyScriptEngineObjectPool;

@NotThreadSafe
public class JythonScriptTaskEnginePython implements IScriptTaskEngine {

    private PyScriptEngine pyScriptEngine;
    private final JythonScriptTaskInputsPython inputs;
    private final JythonScriptTaskResultsPython results;

    public JythonScriptTaskEnginePython(final PyScriptEngine pyScriptEngine) {
        this.pyScriptEngine = pyScriptEngine;
        this.inputs = new JythonScriptTaskInputsPython(this);
        this.results = new JythonScriptTaskResultsPython(this);
    }

    /**
     * https://github.com/mrj0/pyScriptEngine/issues/55
     */
    @Override
    public void eval(final String expression) {
        try {
            pyScriptEngine.eval(expression);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JythonScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public JythonScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        pyScriptEngine = null;
    }

    @Override
    public PyScriptEngine unwrap() {
        return pyScriptEngine;
    }

    public static JythonScriptTaskEnginePython newInstance() {
        return new JythonScriptTaskEnginePython(PyScriptEngineObjectPool.INSTANCE.borrowObject()) {
            @Override
            public void close() {
                final PyScriptEngine interpreter = unwrap();
                if (interpreter != null) {
                    PyScriptEngineObjectPool.INSTANCE.returnObject(interpreter);
                }
                super.close();
            }
        };
    }

}
