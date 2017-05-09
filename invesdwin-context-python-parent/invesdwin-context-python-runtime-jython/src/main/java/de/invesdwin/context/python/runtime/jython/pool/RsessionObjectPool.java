package de.invesdwin.context.python.runtime.jython.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.math.R.Rsession;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.pool.AObjectPool;
import de.invesdwin.context.python.runtime.jython.pool.internal.RsessionPoolableObjectFactory;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.Threads;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.time.duration.Duration;
import de.invesdwin.util.time.fdate.FDate;

@ThreadSafe
@Named
public final class RsessionObjectPool extends AObjectPool<Rsession> implements FactoryBean<RsessionObjectPool> {

    public static final RsessionObjectPool INSTANCE = new RsessionObjectPool();

    private final WrappedExecutorService proxyCooldownMonitorExecutor = Executors
            .newFixedCallerRunsThreadPool(getClass().getSimpleName() + "_timeout", 1);
    @GuardedBy("this")
    private final List<RsessionWrapper> rsessionRotation = new ArrayList<RsessionWrapper>();

    private RsessionObjectPool() {
        super(RsessionPoolableObjectFactory.INSTANCE);
        proxyCooldownMonitorExecutor.execute(new RsessionTimoutMonitor());
    }

    @Override
    protected synchronized Rsession internalBorrowObject() throws Exception {
        if (rsessionRotation.isEmpty()) {
            return factory.makeObject();
        }
        final RsessionWrapper rsession = rsessionRotation.remove(0);
        if (rsession != null) {
            return rsession.getRsession();
        } else {
            return factory.makeObject();
        }
    }

    @Override
    public synchronized int getNumIdle() {
        return rsessionRotation.size();
    }

    @Override
    public synchronized Collection<Rsession> internalClear() throws Exception {
        final Collection<Rsession> removed = new ArrayList<Rsession>();
        while (!rsessionRotation.isEmpty()) {
            removed.add(rsessionRotation.remove(0).getRsession());
        }
        return removed;
    }

    @Override
    protected synchronized Rsession internalAddObject() throws Exception {
        final Rsession pooled = factory.makeObject();
        rsessionRotation.add(new RsessionWrapper(factory.makeObject()));
        return pooled;
    }

    @Override
    protected synchronized void internalReturnObject(final Rsession obj) throws Exception {
        rsessionRotation.add(new RsessionWrapper(obj));
    }

    @Override
    protected void internalInvalidateObject(final Rsession obj) throws Exception {
        //Nothing happens
    }

    @Override
    protected synchronized void internalRemoveObject(final Rsession obj) throws Exception {
        rsessionRotation.remove(new RsessionWrapper(obj));
    }

    private class RsessionTimoutMonitor implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Threads.throwIfInterrupted();
                    TimeUnit.MILLISECONDS.sleep(100);
                    synchronized (RsessionObjectPool.this) {
                        if (!rsessionRotation.isEmpty()) {
                            final List<RsessionWrapper> copy = new ArrayList<RsessionWrapper>(rsessionRotation);
                            for (final RsessionWrapper rsession : copy) {
                                if (rsession.isTimeoutExceeded()) {
                                    Assertions.assertThat(rsessionRotation.remove(rsession)).isTrue();
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

    private final class RsessionWrapper {

        private final Rsession rsession;
        private final FDate timeoutStart;

        RsessionWrapper(final Rsession rsession) {
            this.rsession = rsession;
            this.timeoutStart = new FDate();
        }

        public Rsession getRsession() {
            return rsession;
        }

        public boolean isTimeoutExceeded() {
            return new Duration(timeoutStart, new FDate()).isGreaterThan(Duration.ONE_MINUTE);
        }

        @Override
        public int hashCode() {
            return rsession.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof RsessionWrapper) {
                final RsessionWrapper cObj = (RsessionWrapper) obj;
                return rsession.equals(cObj.getRsession());
            } else if (obj instanceof Rsession) {
                return rsession.equals(obj);
            } else {
                return false;
            }
        }

    }

    @Override
    public RsessionObjectPool getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return RsessionObjectPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
