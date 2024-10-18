package de.invesdwin.context.python.runtime.graalpy;

import javax.annotation.concurrent.ThreadSafe;

import de.invesdwin.context.system.properties.SystemProperties;

@ThreadSafe
public final class GraalpyProperties {

    public static final String PYTHON_COMMAND;

    static {

        final SystemProperties systemProperties = new SystemProperties(GraalpyProperties.class);
        if (systemProperties.containsValue("PYTHON_COMMAND")) {
            PYTHON_COMMAND = systemProperties.getString("PYTHON_COMMAND");
        } else {
            PYTHON_COMMAND = null;
        }
    }

    private GraalpyProperties() {}

}
