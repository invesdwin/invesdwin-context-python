package de.invesdwin.context.python.runtime.jython.pool.internal;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Named;

import org.math.R.Rsession;
import org.springframework.beans.factory.FactoryBean;

import de.invesdwin.context.pool.IPoolableObjectFactory;
import de.invesdwin.context.python.runtime.jython.RserveProperties;
import de.invesdwin.context.python.runtime.jython.RserverConfMode;
import de.invesdwin.util.error.UnknownArgumentException;

@ThreadSafe
@Named
public final class RsessionPoolableObjectFactory
        implements IPoolableObjectFactory<Rsession>, FactoryBean<RsessionPoolableObjectFactory> {

    public static final RsessionPoolableObjectFactory INSTANCE = new RsessionPoolableObjectFactory();

    private RsessionPoolableObjectFactory() {}

    @Override
    public Rsession makeObject() {
        switch (RserveProperties.RSERVER_CONF_MODE) {
        case LOCAL_SPAWN:
            return Rsession.newInstanceTry(new RsessionLogger(), RserveProperties.RSERVER_CONF);
        case LOCAL:
            //fallthrough
        case REMOTE:
            return Rsession.newRemoteInstance(new RsessionLogger(), RserveProperties.RSERVER_CONF);
        default:
            throw UnknownArgumentException.newInstance(RserverConfMode.class, RserveProperties.RSERVER_CONF_MODE);
        }
    }

    @Override
    public void destroyObject(final Rsession obj) throws Exception {
        obj.end();
    }

    @Override
    public boolean validateObject(final Rsession obj) {
        return true;
    }

    @Override
    public void activateObject(final Rsession obj) throws Exception {}

    @Override
    public void passivateObject(final Rsession obj) throws Exception {
        obj.rmAll();
        obj.close(); //reset logger
    }

    @Override
    public RsessionPoolableObjectFactory getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return RsessionPoolableObjectFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
