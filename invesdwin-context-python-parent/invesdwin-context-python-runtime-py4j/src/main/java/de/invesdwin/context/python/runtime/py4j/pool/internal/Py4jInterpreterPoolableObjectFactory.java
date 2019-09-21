package de.invesdwin.context.python.runtime.py4j.pool.internal;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.pool.IPoolableObjectFactory;

@ThreadSafe
@Named
public final class Py4jInterpreterPoolableObjectFactory
        implements IPoolableObjectFactory<Py4jInterpreter>, FactoryBean<Py4jInterpreterPoolableObjectFactory> {

    public static final Py4jInterpreterPoolableObjectFactory INSTANCE = new Py4jInterpreterPoolableObjectFactory();

    private Py4jInterpreterPoolableObjectFactory() {}

    @Override
    public Py4jInterpreter makeObject() {
        return new Py4jInterpreter();
    }

    @Override
    public void destroyObject(final Py4jInterpreter obj) {
        obj.close();
    }

    @Override
    public boolean validateObject(final Py4jInterpreter obj) {
        return true;
    }

    @Override
    public void activateObject(final Py4jInterpreter obj) {}

    @Override
    public void passivateObject(final Py4jInterpreter obj) {
        obj.cleanup();
    }

    @Override
    public Py4jInterpreterPoolableObjectFactory getObject() {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return Py4jInterpreterPoolableObjectFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
