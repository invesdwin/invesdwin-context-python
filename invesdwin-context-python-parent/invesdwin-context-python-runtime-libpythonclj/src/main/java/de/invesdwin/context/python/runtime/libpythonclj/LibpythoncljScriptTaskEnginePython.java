package de.invesdwin.context.python.runtime.libpythonclj;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.ContextProperties;
import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.libpythonclj.internal.PythonEngine;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;
import de.invesdwin.util.lang.Files;
import de.invesdwin.util.lang.UniqueNameGenerator;

@NotThreadSafe
public class LibpythoncljScriptTaskEnginePython implements IScriptTaskEngine {

    private static final UniqueNameGenerator UNIQUE_NAME_GENERATOR = new UniqueNameGenerator() {
        @Override
        protected long getInitialValue() {
            return 1;
        }
    };
    private static final File FOLDER = new File(ContextProperties.TEMP_DIRECTORY,
            LibpythoncljScriptTaskEnginePython.class.getSimpleName());

    private PythonEngine pythonEngine;
    private final LibpythoncljScriptTaskInputsPython inputs;
    private final LibpythoncljScriptTaskResultsPython results;

    public LibpythoncljScriptTaskEnginePython(final PythonEngine pythonEngine) {
        this.pythonEngine = pythonEngine;
        this.inputs = new LibpythoncljScriptTaskInputsPython(this);
        this.results = new LibpythoncljScriptTaskResultsPython(this);
        try {
            Files.forceMkdir(FOLDER);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * https://github.com/mrj0/jep/issues/55
     */
    @Override
    public void eval(final String expression) {
        try {
            Files.writeStringToFile(scriptFile, expression, Charset.defaultCharset());
            pythonEngine.runScript(scriptFile.getAbsolutePath());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LibpythoncljScriptTaskInputsPython getInputs() {
        return inputs;
    }

    @Override
    public LibpythoncljScriptTaskResultsPython getResults() {
        return results;
    }

    @Override
    public void close() {
        if (pythonEngine != null) {
            eval("restoreContext()");
            Files.deleteQuietly(scriptFile);
            scriptFile = null;
            pythonEngine = null;
        }
    }

    @Override
    public PythonEngine unwrap() {
        return pythonEngine;
    }

    /**
     * Jep can only allows access within the same thread. Thus not lock needed. Though be careful about not trying to
     * access the instance from other threads. This will lead to exceptions.
     */
    @Override
    public ILock getSharedLock() {
        if (pythonEngine == null) {
            return DisabledLock.INSTANCE;
        } else {
            return pythonEngine.getLock();
        }
    }

    public static LibpythoncljScriptTaskEnginePython newInstance() {
        return new LibpythoncljScriptTaskEnginePython(PythonEngine.INSTANCE);
    }

}
