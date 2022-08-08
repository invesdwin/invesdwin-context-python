package de.invesdwin.context.python.runtime.jep;

import java.io.File;
import java.io.PrintStream;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

import org.zeroturnaround.exec.stream.slf4j.Slf4jDebugOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jWarnOutputStream;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.system.properties.SystemProperties;
import de.invesdwin.util.collections.Collections;
import de.invesdwin.util.concurrent.Executors;

@ThreadSafe
public final class JepProperties {

    public static final int THREAD_POOL_COUNT;
    public static final File JEP_LIBRARY_PATH;

    public static final PrintStream REDIRECTED_OUT = new PrintStream(
            new Slf4jDebugOutputStream(IScriptTaskRunnerPython.LOG));
    public static final PrintStream REDIRECTED_ERR = new PrintStream(
            new Slf4jWarnOutputStream(IScriptTaskRunnerPython.LOG));

    private static final Set<String> SHARED_MODULES = Collections.synchronizedSet(new LinkedHashSet<String>());

    static {
        SHARED_MODULES.add("numpy");

        final SystemProperties systemProperties = new SystemProperties(JepProperties.class);
        if (systemProperties.containsValue("THREAD_POOL_COUNT")) {
            THREAD_POOL_COUNT = systemProperties.getInteger("THREAD_POOL_COUNT");
        } else {
            THREAD_POOL_COUNT = Executors.getCpuThreadPoolCount();
        }
        if (systemProperties.containsValue("JEP_LIBRARY_PATH")) {
            JEP_LIBRARY_PATH = systemProperties.getFile("JEP_LIBRARY_PATH");
        } else {
            JEP_LIBRARY_PATH = null;
        }
    }

    private JepProperties() {
    }

    public static Set<String> getSharedModules() {
        return Collections.unmodifiableSet(SHARED_MODULES);
    }

    /**
     * You need to define all modules that cannot be unloaded once loaded here
     */
    public static boolean addSharedModule(final String groupId) {
        final boolean changed = SHARED_MODULES.add(groupId);
        if (changed) {
            JepScriptTaskRunnerPython.resetExecutor();
        }
        return changed;
    }

}
