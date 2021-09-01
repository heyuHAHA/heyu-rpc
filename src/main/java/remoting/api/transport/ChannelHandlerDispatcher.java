package remoting.api.transport;

import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.RemotingException;
import remoting.api.buffer.ChannelBuffer;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ChannelHandlerDispatcher 也是 ChannelHandler 接口的实现类之一，
 * 维护了一个 CopyOnWriteArraySet 集合，它所有的 ChannelHandler 接
 * 口实现都会调用其中每个 ChannelHandler 元素的相应方法。另外，
 * ChannelHandlerDispatcher 还提供了增删该 ChannelHandler 集合的相关方法。
 * ————————————————
 *
 */
public class ChannelHandlerDispatcher implements ChannelHandler {

    private Collection<ChannelHandler> channelHandlers = new CopyOnWriteArraySet<>();

    public ChannelHandlerDispatcher() {

    }

    public ChannelHandlerDispatcher(ChannelHandler... handlers) {
        this(handlers == null ? null : Arrays.asList(handlers));
    }

    public  ChannelHandlerDispatcher(Collection<ChannelHandler> collection) {
        if (collection == null || collection.isEmpty())
            this.channelHandlers.addAll(collection);
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        for(ChannelHandler listener : channelHandlers) {
            listener.connected(channel);
        }
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.disconnected(channel);
        }
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.sent(channel,message);
        }
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.received(channel,message);
        }
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.caught(channel,exception);
        }
    }

    /**
     * 供上层使用
     * @return
     */
    public Collection<ChannelHandler> getChannelHandlers() {
        return channelHandlers;
    }

    public ChannelHandlerDispatcher addChannelHandler(ChannelHandler handler) {
        this.channelHandlers.add(handler);
        return this;
    }

    public ChannelHandlerDispatcher removeChannelHandler(ChannelHandler handler) {
        this.channelHandlers.remove(handler);
        return this;
    }
}
