package de.invesdwin.context.python.runtime.contract.callback.socket;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.util.concurrent.pool.timeout.ASingletonTimeoutObjectPool;
import de.invesdwin.util.lang.Closeables;
import de.invesdwin.util.time.date.FTimeUnit;
import de.invesdwin.util.time.duration.Duration;

@Immutable
public final class SocketScriptTaskCallbackServerPool
        extends ASingletonTimeoutObjectPool<SocketScriptTaskCallbackServer> {

    public static final SocketScriptTaskCallbackServerPool INSTANCE = new SocketScriptTaskCallbackServerPool();

    private SocketScriptTaskCallbackServerPool() {
        super(Duration.ONE_MINUTE, new Duration(10, FTimeUnit.SECONDS));
    }

    @Override
    protected SocketScriptTaskCallbackServer newObject() {
        final SocketScriptTaskCallbackServer server = new SocketScriptTaskCallbackServer("localhost", 0);
        server.open();
        return server;
    }

    @Override
    protected boolean passivateObject(final SocketScriptTaskCallbackServer element) {
        return true;
    }

    @Override
    public void invalidateObject(final SocketScriptTaskCallbackServer element) {
        Closeables.closeQuietly(element);
    }

}
