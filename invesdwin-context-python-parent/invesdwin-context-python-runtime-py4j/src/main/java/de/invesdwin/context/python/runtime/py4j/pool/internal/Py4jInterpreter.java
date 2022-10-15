package de.invesdwin.context.python.runtime.py4j.pool.internal;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.StartedProcess;
import org.zeroturnaround.exec.stream.slf4j.Slf4jDebugOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jWarnOutputStream;

import de.invesdwin.context.ContextProperties;
import de.invesdwin.context.log.error.Err;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.py4j.Py4jProperties;
import de.invesdwin.util.lang.Files;
import de.invesdwin.util.lang.finalizer.AFinalizer;
import de.invesdwin.util.lang.string.UniqueNameGenerator;
import de.invesdwin.util.time.date.FTimeUnit;
import py4j.GatewayServer;
import py4j.GatewayServer.GatewayServerBuilder;
import py4j.GatewayServerListener;
import py4j.Py4JServerConnection;

@NotThreadSafe
public class Py4jInterpreter implements IPy4jInterpreter, Closeable {

    private static final UniqueNameGenerator UNIQUE_NAME_GENERATOR = new UniqueNameGenerator() {
        @Override
        protected long getInitialValue() {
            return 1L;
        }
    };
    private final Py4jInterpreterFinalizer finalizer;

    public Py4jInterpreter() {
        this.finalizer = new Py4jInterpreterFinalizer();
        this.finalizer.register(this);
    }

    @Override
    public Object get(final String variable) {
        return finalizer.delegate.get(variable);
    }

    @Override
    public void eval(final String expression) {
        finalizer.delegate.eval(expression);
    }

    @Override
    public void put(final String variable, final Object value) {
        finalizer.delegate.put(variable, value);
    }

    @Override
    public synchronized void close() {
        finalizer.close();
    }

    @Override
    public void cleanup() {
        finalizer.delegate.cleanup();
    }

    private static final class Py4jInterpreterFinalizer extends AFinalizer {

        private GatewayServer server;

        private IPy4jInterpreter delegate;
        private StartedProcess process;
        private File scriptFile;

        private Py4jInterpreterFinalizer() {
            final ReadyApplication readyApplication = new ReadyApplication();
            this.server = new GatewayServerBuilder().entryPoint(readyApplication)
                    .connectTimeout(ContextProperties.DEFAULT_NETWORK_TIMEOUT.intValue(FTimeUnit.MILLISECONDS))
                    //                .readTimeout(ContextProperties.DEFAULT_NETWORK_TIMEOUT.intValue(FTimeUnit.MILLISECONDS))
                    .javaPort(0)
                    .build();
            final AtomicBoolean serverStarted = new AtomicBoolean(false);
            server.addListener(new GatewayServerListener() {
                @Override
                public void serverStopped() {
                }

                @Override
                public void serverStarted() {
                    serverStarted.set(true);
                }

                @Override
                public void serverPreShutdown() {
                }

                @Override
                public void serverPostShutdown() {
                }

                @Override
                public void serverError(final Exception e) {
                    Err.process(e);
                }

                @Override
                public void connectionStopped(final Py4JServerConnection gatewayConnection) {
                }

                @Override
                public void connectionStarted(final Py4JServerConnection gatewayConnection) {
                }

                @Override
                public void connectionError(final Exception e) {
                    Err.process(e);
                }
            });
            server.start();
            while (!serverStarted.get()) {
                try {
                    FTimeUnit.MILLISECONDS.sleep(1);
                } catch (final InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
            }

            try (InputStream in = new ClassPathResource(Py4jInterpreter.class.getSimpleName() + ".py",
                    Py4jInterpreter.class).getInputStream()) {
                final File folder = new File(ContextProperties.TEMP_DIRECTORY, Py4jInterpreter.class.getSimpleName());
                Files.forceMkdir(folder);
                this.scriptFile = new File(folder, UNIQUE_NAME_GENERATOR.get("Py4jInterpreter") + ".py");
                try (OutputStream out = new FileOutputStream(scriptFile)) {
                    IOUtils.copy(in, out);
                }
                this.process = new ProcessExecutor()
                        .commandSplit(Py4jProperties.PYTHON_COMMAND + " -u " + scriptFile.getAbsolutePath() + " "
                                + server.getAddress().getHostAddress() + " "
                                + String.valueOf(server.getListeningPort()))
                        .destroyOnExit()
                        .exitValueNormal()
                        .redirectOutput(new Slf4jDebugOutputStream(IScriptTaskRunnerPython.LOG))
                        .redirectError(new Slf4jWarnOutputStream(IScriptTaskRunnerPython.LOG))
                        .start();
            } catch (InvalidExitValueException | IOException e) {
                throw new RuntimeException(e);
            }
            while (!readyApplication.isReady()) {
                try {
                    FTimeUnit.MILLISECONDS.sleep(10);
                } catch (final InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
            }
            this.delegate = (IPy4jInterpreter) server.getPythonServerEntryPoint(new Class[] { IPy4jInterpreter.class });
        }

        @Override
        protected void clean() {
            if (process != null) {
                process.getProcess().destroy();
                process = null;
            }
            if (server != null) {
                server.shutdown();
                server = null;
            }

            if (scriptFile != null) {
                Files.deleteQuietly(scriptFile);
                scriptFile = null;
            }

            delegate = null;
        }

        @Override
        protected boolean isCleaned() {
            return delegate == null;
        }

        @Override
        public boolean isThreadLocal() {
            return false;
        }

    }

}
