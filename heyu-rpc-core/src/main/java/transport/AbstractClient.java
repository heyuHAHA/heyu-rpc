package transport;

import codec.Codec;
import exception.HeyuRpcFrameworkException;
import rpc.Request;
import rpc.URL;

import java.net.InetSocketAddress;

public abstract class AbstractClient implements Client{
    protected InetSocketAddress localAddress;
    protected  InetSocketAddress remoteAddress;

    protected URL url;
    protected Codec codec;

    protected volatile AbstractServer.ChannelState state = AbstractServer.ChannelState.UNINIT;

    public AbstractClient(URL url) {
        this.url = url;

    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public void heartbeat(Request request) {
        throw new HeyuRpcFrameworkException("heartbeat not support ");
    }
}
