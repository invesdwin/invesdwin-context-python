package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.context.system.properties.SystemProperties;

@Immutable
public final class Py4jProperties {

    public static final String PYTHON_COMMAND;

    static {
        final SystemProperties systemProperties = new SystemProperties(Py4jProperties.class);
        if (systemProperties.containsValue("PYTHON_COMMAND")) {
            PYTHON_COMMAND = systemProperties.getString("PYTHON_COMMAND");
        } else {
            PYTHON_COMMAND = "python";
        }
    }

    private Py4jProperties() {}

}
