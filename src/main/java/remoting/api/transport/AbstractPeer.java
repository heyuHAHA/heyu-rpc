package remoting.api.transport;

import common.URL;
import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.EndPoint;
import remoting.api.RemotingException;

import java.net.InetSocketAddress;

/**
 * 实现了EndPoint， ChannelHandler，表明这个连接具有连接，断开，发送，接收的功能
 */
public abstract class AbstractPeer implements EndPoint, ChannelHandler {
    private final ChannelHandler handler;

    private volatile URL url;

    private volatile boolean closing;

    private volatile boolean closed;


    public AbstractPeer(URL url, ChannelHandler handler) {
        if (url == null)
            throw new IllegalArgumentException("url == null");

        if (handler == null)
            throw new IllegalArgumentException("handler == null");

        this.url = url;
        this.handler = handler;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        if (closed)
            return;
        handler.connected(channel);

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        handler.disconnected(channel);
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        if (closed)
            return;
        handler.sent(channel,message);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        if (closed)
            return;
        handler.sent(channel,message);
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        handler.caught(channel,exception);
    }
    public ChannelHandler getChannelHandler() {
        if (handler instanceof ChannelHandlerDelegate) {
            return ((ChannelHandlerDelegate) handler).getHandler();
        } else {
            return handler;
        }
    }


    private ChannelHandler getDelegateHandler() {
        return handler;
    }

    @Override
    public URL getUrl() {
        return url;
    }


    //给底层的注入进来
    protected void setUrl(URL url) {
        if (url == null)
            throw new IllegalArgumentException("url == null");
        this.url = url;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public void send(Object message) throws RemotingException {
        //TODO
        send(message,false);
    }


    @Override
    public void close() {
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    public boolean isClosing() {
        return closing && !closed;
    }
}
