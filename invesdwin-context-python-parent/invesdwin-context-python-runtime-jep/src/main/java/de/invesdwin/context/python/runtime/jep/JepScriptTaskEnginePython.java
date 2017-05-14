package de.invesdwin.context.python.runtime.jep;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.io.FileUtils;

import de.invesdwin.context.ContextProperties;
import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.util.lang.UniqueNameGenerator;
import jep.Jep;

@NotThreadSafe
public class JepScriptTaskEnginePython implements IScriptTaskEngine {

    private static final UniqueNameGenerator UNIQUE_NAME_GENERATOR = new UniqueNameGenerator() {
        @Override
        protected long getInitialValue() {
            return 1;
        }
    };
    private static final File FOLDER = new File(ContextProperties.TEMP_DIRECTORY,
            JepScriptTaskEnginePython.class.getSimpleName());

    private Jep jep;
    private final JepScriptTaskInputsPython inputs;
    private final JepScriptTaskResultsPython results;
    private File scriptFile;

    public JepScriptTaskEnginePython(final Jep jep) {
        this.jep = jep;
        this.inputs = new JepScriptTaskInputsPython(this);
        this.results = new JepScriptTaskResultsPython(this);
        try {
            FileUtils.forceMkdir(FOLDER);
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
            FileUtils.writeStringToFile(scriptFile, expression, Charset.defaultCharset());
            jep.runScript(scriptFile.getAbsolutePath());
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
        eval("restoreContext()");
        FileUtils.deleteQuietly(scriptFile);
        scriptFile = null;
        jep = null;
    }

    @Override
    public Jep unwrap() {
        return jep;
    }

}
