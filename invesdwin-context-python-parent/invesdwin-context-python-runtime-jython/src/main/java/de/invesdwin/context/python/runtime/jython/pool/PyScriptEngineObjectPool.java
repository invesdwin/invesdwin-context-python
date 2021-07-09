package de.invesdwin.context.python.runtime.jython.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.python.jsr223.PyScriptEngine;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.pool.AObjectPool;
import de.invesdwin.context.python.runtime.jython.pool.internal.PyScriptEnginePoolableObjectFactory;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.Threads;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.time.date.FDate;
import de.invesdwin.util.time.duration.Duration;

@ThreadSafe
@Named
public final class PyScriptEngineObjectPool extends AObjectPool<PyScriptEngine>
        implements FactoryBean<PyScriptEngineObjectPool> {

    public static final PyScriptEngineObjectPool INSTANCE = new PyScriptEngineObjectPool();

    private final WrappedExecutorService timeoutMonitorExecutor = Executors
            .newFixedCallerRunsThreadPool(getClass().getSimpleName() + "_timeout", 1);
    @GuardedBy("this")
    private final List<PyScriptEngineWrapper> pyScriptEngineRotation = new ArrayList<PyScriptEngineWrapper>();

    private PyScriptEngineObjectPool() {
        super(PyScriptEnginePoolableObjectFactory.INSTANCE);
        timeoutMonitorExecutor.execute(new PyScriptEngineTimoutMonitor());
    }

    @Override
    protected synchronized PyScriptEngine internalBorrowObject() {
        if (pyScriptEngineRotation.isEmpty()) {
            return factory.makeObject();
        }
        final PyScriptEngineWrapper pyScriptEngine = pyScriptEngineRotation.remove(0);
        if (pyScriptEngine != null) {
            return pyScriptEngine.getPyScriptEngine();
        } else {
            return factory.makeObject();
        }
    }

    @Override
    public synchronized int getNumIdle() {
        return pyScriptEngineRotation.size();
    }

    @Override
    public synchronized Collection<PyScriptEngine> internalClear() {
        final Collection<PyScriptEngine> removed = new ArrayList<PyScriptEngine>();
        while (!pyScriptEngineRotation.isEmpty()) {
            removed.add(pyScriptEngineRotation.remove(0).getPyScriptEngine());
        }
        return removed;
    }

    @Override
    protected synchronized PyScriptEngine internalAddObject() {
        final PyScriptEngine pooled = factory.makeObject();
        pyScriptEngineRotation.add(new PyScriptEngineWrapper(factory.makeObject()));
        return pooled;
    }

    @Override
    protected synchronized void internalReturnObject(final PyScriptEngine obj) {
        pyScriptEngineRotation.add(new PyScriptEngineWrapper(obj));
    }

    @Override
    protected void internalInvalidateObject(final PyScriptEngine obj) {
        //Nothing happens
    }

    @Override
    protected synchronized void internalRemoveObject(final PyScriptEngine obj) {
        pyScriptEngineRotation.remove(new PyScriptEngineWrapper(obj));
    }

    private class PyScriptEngineTimoutMonitor implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Threads.throwIfInterrupted();
                    TimeUnit.MILLISECONDS.sleep(100);
                    synchronized (PyScriptEngineObjectPool.this) {
                        if (!pyScriptEngineRotation.isEmpty()) {
                            final List<PyScriptEngineWrapper> copy = new ArrayList<PyScriptEngineWrapper>(
                                    pyScriptEngineRotation);
                            for (final PyScriptEngineWrapper pyScriptEngine : copy) {
                                if (pyScriptEngine.isTimeoutExceeded()) {
                                    Assertions.assertThat(pyScriptEngineRotation.remove(pyScriptEngine)).isTrue();
                                }
                            }
                        }
                    }
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static final class PyScriptEngineWrapper {

        private final PyScriptEngine pyScriptEngine;
        private final FDate timeoutStart;

        PyScriptEngineWrapper(final PyScriptEngine rCaller) {
            this.pyScriptEngine = rCaller;
            this.timeoutStart = new FDate();
        }

        public PyScriptEngine getPyScriptEngine() {
            return pyScriptEngine;
        }

        public boolean isTimeoutExceeded() {
            return new Duration(timeoutStart, new FDate()).isGreaterThan(Duration.ONE_MINUTE);
        }

        @Override
        public int hashCode() {
            return pyScriptEngine.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof PyScriptEngineWrapper) {
                final PyScriptEngineWrapper cObj = (PyScriptEngineWrapper) obj;
                return pyScriptEngine.equals(cObj.getPyScriptEngine());
            } else if (obj instanceof PyScriptEngine) {
                return pyScriptEngine.equals(obj);
            } else {
                return false;
            }
        }

    }

    @Override
    public PyScriptEngineObjectPool getObject() {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return PyScriptEngineObjectPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
