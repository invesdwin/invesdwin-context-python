package de.invesdwin.context.python.runtime.py4j.pool;

import javax.annotation.concurrent.ThreadSafe;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.python.runtime.py4j.pool.internal.Py4jInterpreter;
import de.invesdwin.util.concurrent.pool.timeout.ATimeoutObjectPool;
import de.invesdwin.util.time.date.FTimeUnit;
import de.invesdwin.util.time.duration.Duration;
import jakarta.inject.Named;

@ThreadSafe
@Named
public final class Py4jInterpreterObjectPool extends ATimeoutObjectPool<Py4jInterpreter>
        implements FactoryBean<Py4jInterpreterObjectPool> {

    public static final Py4jInterpreterObjectPool INSTANCE = new Py4jInterpreterObjectPool();

    private Py4jInterpreterObjectPool() {
        super(Duration.ONE_MINUTE, new Duration(10, FTimeUnit.SECONDS));
    }

    @Override
    public void invalidateObject(final Py4jInterpreter element) {
        element.close();
    }

    @Override
    protected Py4jInterpreter newObject() {
        return new Py4jInterpreter();
    }

    @Override
    protected boolean passivateObject(final Py4jInterpreter element) {
        element.cleanup();
        return true;
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
