package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.ThreadSafe;

import de.invesdwin.context.system.properties.SystemProperties;

@ThreadSafe
public final class JapybProperties {

    public static final String PYTHON_COMMAND;

    static {

        final SystemProperties systemProperties = new SystemProperties(JapybProperties.class);
        if (systemProperties.containsValue("PYTHON_COMMAND")) {
            PYTHON_COMMAND = systemProperties.getString("PYTHON_COMMAND");
        } else {
            PYTHON_COMMAND = null;
        }
    }

    private JapybProperties() {}

}
