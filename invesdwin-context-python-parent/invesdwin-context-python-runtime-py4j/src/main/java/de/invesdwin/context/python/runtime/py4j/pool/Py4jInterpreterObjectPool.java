package de.invesdwin.context.python.runtime.py4j.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.py4j.pool.internal.Py4jInterpreter;
import de.invesdwin.context.python.runtime.py4j.pool.internal.Py4jInterpreterPoolableObjectFactory;
import de.invesdwin.util.collections.iterable.ICloseableIterator;
import de.invesdwin.util.collections.iterable.buffer.NodeBufferingIterator;
import de.invesdwin.util.collections.iterable.buffer.NodeBufferingIterator.INode;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.Threads;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.pool.AObjectPool;
import de.invesdwin.util.time.date.FDate;
import de.invesdwin.util.time.duration.Duration;

@ThreadSafe
@Named
public final class Py4jInterpreterObjectPool extends AObjectPool<Py4jInterpreter>
        implements FactoryBean<Py4jInterpreterObjectPool> {

    public static final Py4jInterpreterObjectPool INSTANCE = new Py4jInterpreterObjectPool();

    private final WrappedExecutorService timeoutMonitorExecutor = Executors
            .newFixedCallerRunsThreadPool(getClass().getSimpleName() + "_timeout", 1);
    @GuardedBy("this")
    private final NodeBufferingIterator<Py4jInterpreterWrapper> py4jInterpreterRotation = new NodeBufferingIterator<Py4jInterpreterWrapper>();

    private Py4jInterpreterObjectPool() {
        super(Py4jInterpreterPoolableObjectFactory.INSTANCE);
        timeoutMonitorExecutor.execute(new Py4jInterpreterTimoutMonitor());
    }

    @Override
    protected synchronized Py4jInterpreter internalBorrowObject() {
        if (py4jInterpreterRotation.isEmpty()) {
            return factory.makeObject();
        }
        final Py4jInterpreterWrapper py4jInterpreter = py4jInterpreterRotation.next();
        if (py4jInterpreter != null) {
            return py4jInterpreter.getPy4jInterpreter();
        } else {
            return factory.makeObject();
        }
    }

    @Override
    public synchronized int getNumIdle() {
        return py4jInterpreterRotation.size();
    }

    @Override
    public synchronized Collection<Py4jInterpreter> internalClear() {
        final Collection<Py4jInterpreter> removed = new ArrayList<Py4jInterpreter>();
        while (!py4jInterpreterRotation.isEmpty()) {
            removed.add(py4jInterpreterRotation.next().getPy4jInterpreter());
        }
        return removed;
    }

    @Override
    protected synchronized Py4jInterpreter internalAddObject() {
        final Py4jInterpreter pooled = factory.makeObject();
        py4jInterpreterRotation.add(new Py4jInterpreterWrapper(factory.makeObject()));
        return pooled;
    }

    @Override
    protected synchronized void internalReturnObject(final Py4jInterpreter obj) {
        py4jInterpreterRotation.add(new Py4jInterpreterWrapper(obj));
    }

    @Override
    protected void internalInvalidateObject(final Py4jInterpreter obj) {
        //Nothing happens
    }

    @Override
    protected synchronized void internalRemoveObject(final Py4jInterpreter obj) {
        py4jInterpreterRotation.remove(new Py4jInterpreterWrapper(obj));
    }

    private class Py4jInterpreterTimoutMonitor implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Threads.throwIfInterrupted();
                    TimeUnit.MILLISECONDS.sleep(100);
                    synchronized (Py4jInterpreterObjectPool.this) {
                        if (!py4jInterpreterRotation.isEmpty()) {
                            final ICloseableIterator<Py4jInterpreterWrapper> iterator = py4jInterpreterRotation
                                    .iterator();
                            try {
                                while (true) {
                                    final Py4jInterpreterWrapper py4jInterpreter = iterator.next();
                                    if (py4jInterpreter.isTimeoutExceeded()) {
                                        iterator.remove();
                                    }
                                }
                            } catch (final NoSuchElementException e) {
                                //end reached
                            }
                        }
                    }
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static final class Py4jInterpreterWrapper implements INode<Py4jInterpreterWrapper> {

        private final Py4jInterpreter py4jInterpreter;
        private final FDate timeoutStart;
        private Py4jInterpreterWrapper next;

        Py4jInterpreterWrapper(final Py4jInterpreter py4jInterpreter) {
            this.py4jInterpreter = py4jInterpreter;
            this.timeoutStart = new FDate();
        }

        public Py4jInterpreter getPy4jInterpreter() {
            return py4jInterpreter;
        }

        public boolean isTimeoutExceeded() {
            return new Duration(timeoutStart, new FDate()).isGreaterThan(Duration.ONE_MINUTE);
        }

        @Override
        public int hashCode() {
            return py4jInterpreter.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Py4jInterpreterWrapper) {
                final Py4jInterpreterWrapper cObj = (Py4jInterpreterWrapper) obj;
                return py4jInterpreter.equals(cObj.getPy4jInterpreter());
            } else if (obj instanceof Py4jInterpreter) {
                return py4jInterpreter.equals(obj);
            } else {
                return false;
            }
        }

        @Override
        public Py4jInterpreterWrapper getNext() {
            return next;
        }

        @Override
        public void setNext(final Py4jInterpreterWrapper next) {
            this.next = next;
        }

    }

    @Override
    public Py4jInterpreterObjectPool getObject() {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return Py4jInterpreterObjectPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
