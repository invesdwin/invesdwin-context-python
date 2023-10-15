package de.invesdwin.context.python.runtime.japyb.pool;

import java.io.IOException;

import javax.annotation.concurrent.ThreadSafe;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.util.concurrent.pool.timeout.ATimeoutObjectPool;
import de.invesdwin.util.time.date.FTimeUnit;
import de.invesdwin.util.time.duration.Duration;
import jakarta.inject.Named;

@ThreadSafe
@Named
public final class JapybObjectPool extends ATimeoutObjectPool<ExtendedPythonBridge>
        implements FactoryBean<JapybObjectPool> {

    public static final JapybObjectPool INSTANCE = new JapybObjectPool();

    private JapybObjectPool() {
        //julia compilation is a lot of overhead, thus keep instances open longer
        super(new Duration(10, FTimeUnit.MINUTES), new Duration(10, FTimeUnit.SECONDS));
    }

    @Override
    public void invalidateObject(final ExtendedPythonBridge element) {
        element.close();
    }

    @Override
    protected ExtendedPythonBridge newObject() {
        final ExtendedPythonBridge session = new ExtendedPythonBridge();
        try {
            session.open();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return session;
    }

    @Override
    protected boolean passivateObject(final ExtendedPythonBridge element) {
        try {
            element.reset();
            return true;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JapybObjectPool getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return JapybObjectPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
