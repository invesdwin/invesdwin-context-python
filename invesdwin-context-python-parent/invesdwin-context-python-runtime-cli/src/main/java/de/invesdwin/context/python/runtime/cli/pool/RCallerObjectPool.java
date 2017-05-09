package de.invesdwin.context.python.runtime.cli.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import com.github.rcaller.rstuff.RCaller;

import de.invesdwin.context.pool.AObjectPool;
import de.invesdwin.context.python.runtime.cli.pool.internal.RCallerPoolableObjectFactory;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.Threads;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.time.duration.Duration;
import de.invesdwin.util.time.fdate.FDate;

@ThreadSafe
@Named
public final class RCallerObjectPool extends AObjectPool<RCaller> implements FactoryBean<RCallerObjectPool> {

    public static final RCallerObjectPool INSTANCE = new RCallerObjectPool();

    private final WrappedExecutorService proxyCooldownMonitorExecutor = Executors
            .newFixedCallerRunsThreadPool(getClass().getSimpleName() + "_timeout", 1);
    @GuardedBy("this")
    private final List<RCallerWrapper> rCallerRotation = new ArrayList<RCallerWrapper>();

    private RCallerObjectPool() {
        super(RCallerPoolableObjectFactory.INSTANCE);
        proxyCooldownMonitorExecutor.execute(new RCallerTimoutMonitor());
    }

    @Override
    protected synchronized RCaller internalBorrowObject() throws Exception {
        if (rCallerRotation.isEmpty()) {
            return factory.makeObject();
        }
        final RCallerWrapper rCaller = rCallerRotation.remove(0);
        if (rCaller != null) {
            return rCaller.getRCaller();
        } else {
            return factory.makeObject();
        }
    }

    @Override
    public synchronized int getNumIdle() {
        return rCallerRotation.size();
    }

    @Override
    public synchronized Collection<RCaller> internalClear() throws Exception {
        final Collection<RCaller> removed = new ArrayList<RCaller>();
        while (!rCallerRotation.isEmpty()) {
            removed.add(rCallerRotation.remove(0).getRCaller());
        }
        return removed;
    }

    @Override
    protected synchronized RCaller internalAddObject() throws Exception {
        final RCaller pooled = factory.makeObject();
        rCallerRotation.add(new RCallerWrapper(factory.makeObject()));
        return pooled;
    }

    @Override
    protected synchronized void internalReturnObject(final RCaller obj) throws Exception {
        rCallerRotation.add(new RCallerWrapper(obj));
    }

    @Override
    protected void internalInvalidateObject(final RCaller obj) throws Exception {
        //Nothing happens
    }

    @Override
    protected synchronized void internalRemoveObject(final RCaller obj) throws Exception {
        rCallerRotation.remove(new RCallerWrapper(obj));
    }

    private class RCallerTimoutMonitor implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Threads.throwIfInterrupted();
                    TimeUnit.MILLISECONDS.sleep(100);
                    synchronized (RCallerObjectPool.this) {
                        if (!rCallerRotation.isEmpty()) {
                            final List<RCallerWrapper> copy = new ArrayList<RCallerWrapper>(rCallerRotation);
                            for (final RCallerWrapper rCaller : copy) {
                                if (rCaller.isTimeoutExceeded()) {
                                    Assertions.assertThat(rCallerRotation.remove(rCaller)).isTrue();
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

    private final class RCallerWrapper {

        private final RCaller rCaller;
        private final FDate timeoutStart;

        RCallerWrapper(final RCaller rCaller) {
            this.rCaller = rCaller;
            this.timeoutStart = new FDate();
        }

        public RCaller getRCaller() {
            return rCaller;
        }

        public boolean isTimeoutExceeded() {
            return new Duration(timeoutStart, new FDate()).isGreaterThan(Duration.ONE_MINUTE);
        }

        @Override
        public int hashCode() {
            return rCaller.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof RCallerWrapper) {
                final RCallerWrapper cObj = (RCallerWrapper) obj;
                return rCaller.equals(cObj.getRCaller());
            } else if (obj instanceof RCaller) {
                return rCaller.equals(obj);
            } else {
                return false;
            }
        }

    }

    @Override
    public RCallerObjectPool getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return RCallerObjectPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
