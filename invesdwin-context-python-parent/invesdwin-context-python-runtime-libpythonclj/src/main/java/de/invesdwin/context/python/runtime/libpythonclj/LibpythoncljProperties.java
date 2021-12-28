package de.invesdwin.context.python.runtime.libpythonclj;

import java.io.PrintStream;

import javax.annotation.concurrent.ThreadSafe;

import org.zeroturnaround.exec.stream.slf4j.Slf4jDebugOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jWarnOutputStream;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.system.properties.SystemProperties;

@ThreadSafe
public final class LibpythoncljProperties {

    public static final String PYTHON_COMMAND;

    public static final PrintStream REDIRECTED_OUT = new PrintStream(
            new Slf4jDebugOutputStream(IScriptTaskRunnerPython.LOG));
    public static final PrintStream REDIRECTED_ERR = new PrintStream(
            new Slf4jWarnOutputStream(IScriptTaskRunnerPython.LOG));

    static {

        final SystemProperties systemProperties = new SystemProperties(LibpythoncljProperties.class);
        if (systemProperties.containsValue("PYTHON_COMMAND")) {
            PYTHON_COMMAND = systemProperties.getString("PYTHON_COMMAND");
        } else {
            PYTHON_COMMAND = null;
        }
    }

    private LibpythoncljProperties() {
    }

}
