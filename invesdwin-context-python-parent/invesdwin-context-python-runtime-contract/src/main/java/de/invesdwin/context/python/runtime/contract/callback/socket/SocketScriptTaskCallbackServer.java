package de.invesdwin.context.python.runtime.contract.callback.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.channel.async.AsynchronousHandlerFactorySupport;
import de.invesdwin.context.integration.channel.async.IAsynchronousHandler;
import de.invesdwin.context.integration.channel.async.netty.tcp.StringNettySocketAsynchronousChannel;
import de.invesdwin.context.integration.channel.sync.ISynchronousChannel;
import de.invesdwin.context.integration.channel.sync.netty.tcp.NettySocketSynchronousChannel;
import de.invesdwin.context.integration.channel.sync.netty.tcp.type.NioNettySocketChannelType;
import de.invesdwin.context.integration.compression.lz4.LZ4Streams;
import de.invesdwin.context.integration.network.NetworkUtil;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class SocketScriptTaskCallbackServer implements ISynchronousChannel {

    private StringNettySocketAsynchronousChannel server;
    private final String host;
    private final int configuredPort;
    private int port;

    public SocketScriptTaskCallbackServer(final String host, final int port) {
        this.host = host;
        this.configuredPort = port;
        this.port = port;
    }

    @Override
    public void open() {
        Assertions.checkNull(server);
        if (configuredPort <= 0) {
            port = NetworkUtil.findAvailableTcpPort();
        } else {
            port = configuredPort;
        }
        final NettySocketSynchronousChannel serverChannel = new NettySocketSynchronousChannel(
                NioNettySocketChannelType.INSTANCE, new InetSocketAddress(host, port), true,
                LZ4Streams.DEFAULT_BLOCK_SIZE_BYTES);
        server = new StringNettySocketAsynchronousChannel(serverChannel,
                new AsynchronousHandlerFactorySupport<String, String>() {
                    @Override
                    public IAsynchronousHandler<String, String> newHandler() {
                        return new SocketScriptTaskCallbackServerHandler();
                    }
                }, true);
        try {
            server.open();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void close() {
        if (server != null) {
            server.close();
            server = null;
            port = configuredPort;
        }
    }

}
