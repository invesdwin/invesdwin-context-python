package de.invesdwin.context.python.runtime.libpythonclj;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.ContextProperties;
import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.python.runtime.libpythonclj.internal.LibpythoncljWrapper;
import de.invesdwin.util.concurrent.lock.ILock;
import de.invesdwin.util.concurrent.lock.disabled.DisabledLock;
import de.invesdwin.util.lang.Files;
import de.invesdwin.util.lang.UniqueNameGenerator;
import jep.Jep;

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

    private Jep jep;
    private final LibpythoncljScriptTaskInputsPython inputs;
    private final LibpythoncljScriptTaskResultsPython results;
    private File scriptFile;

    public LibpythoncljScriptTaskEnginePython(final Jep jep) {
        this.jep = jep;
        this.inputs = new LibpythoncljScriptTaskInputsPython(this);
        this.results = new LibpythoncljScriptTaskResultsPython(this);
        try {
            Files.forceMkdir(FOLDER);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        this.scriptFile = new File(FOLDER, UNIQUE_NAME_GENERATOR.get("script") + ".py");
    }

    /**
     * https://github.com/mrj0/jep/issues/55
     */
    @Override
    public void eval(final String expression) {
        try {
            Files.writeStringToFile(scriptFile, expression, Charset.defaultCharset());
            jep.runScript(scriptFile.getAbsolutePath());
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
        eval("restoreContext()");
        Files.deleteQuietly(scriptFile);
        scriptFile = null;
        jep = null;
    }

    @Override
    public Jep unwrap() {
        return jep;
    }

    /**
     * Jep can only allows access within the same thread. Thus not lock needed. Though be careful about not trying to
     * access the instance from other threads. This will lead to exceptions.
     */
    @Override
    public ILock getSharedLock() {
        return DisabledLock.INSTANCE;
    }

    public static LibpythoncljScriptTaskEnginePython newInstance() {
        return new LibpythoncljScriptTaskEnginePython(LibpythoncljWrapper.get().getJep());
    }

}
