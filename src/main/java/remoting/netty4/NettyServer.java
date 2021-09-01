package remoting.netty4;

import common.URL;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.RemotingServer;
import remoting.api.transport.AbstractServer;
import remoting.api.transport.dispatcher.ChannelHandlers;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class NettyServer extends AbstractServer implements RemotingServer {

    private Map<String, Channel> channels;
    private ServerBootstrap bootstrap;
    private io.netty.channel.Channel channel;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer(URL url, ChannelHandler handler) {
        //TODO 这里可以拓展url的拼写，加上threadid
        super(url, ChannelHandlers.wrap(handler,url));
    }

    @Override
    protected void doOpen() {

    }

    @Override
    protected void doClose() {

    }

    @Override
    public Collection<Channel> getChannels() {
        Collection<Channel> chs = new ArrayList<>(this.channels.size());
        chs.addAll(this.channels.values());
        return chs;
    }

    @Override
    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        return channels.get(inetSocketAddress.getAddress().getHostAddress()+":"+inetSocketAddress.getPort());
    }

    @Override
    public boolean canHandleIdle() {
        return true;
    }

    @Override
    public boolean isBound() {
        return channel.isActive();
    }
}
