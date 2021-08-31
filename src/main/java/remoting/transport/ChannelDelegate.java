package remoting.transport;

import common.URL;
import remoting.Channel;
import remoting.RemotingException;

import java.net.InetSocketAddress;

public class ChannelDelegate implements Channel {

    private Channel channel;

    public ChannelDelegate() {

    }

    public ChannelDelegate(Channel channel) {
        this.channel = channel;
    }
    @Override
    public URL getUrl() {
        return channel.getUrl();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return channel.getLocalAddress();
    }

    @Override
    public void send(Object message) throws RemotingException {
        channel.send(message);
    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        channel.send(message,sent);
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return channel.getRemoteAddress();
    }

    @Override
    public boolean isConnected() {
        return channel.isConnected();
    }

    @Override
    public boolean hasAttribute(String key) {
        return channel.hasAttribute(key);
    }

    @Override
    public Object getAttribute(String key) {
        return channel.getAttribute(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        channel.setAttribute(key,value);
    }

    @Override
    public void removeAttribute(String key) {
        channel.removeAttribute(key);
    }

    @Override
    public boolean isClosed() {
        return channel.isClosed();
    }
}
