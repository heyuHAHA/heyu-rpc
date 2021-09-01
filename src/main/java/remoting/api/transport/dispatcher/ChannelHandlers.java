package remoting.api.transport.dispatcher;

import common.URL;
import remoting.api.ChannelHandler;
import remoting.api.exchange.support.header.HeartbeatHandler;
import remoting.api.transport.MultiMessageHandler;
import remoting.api.transport.dispatcher.all.AllChannelHandler;

public class ChannelHandlers {
    private static ChannelHandlers Instance = new ChannelHandlers();

    private ChannelHandlers() {

    }

    public static ChannelHandlers getInstance() {
        return Instance;
    }

    public static ChannelHandler wrap(ChannelHandler handler, URL url) {
        return ChannelHandlers.getInstance().wrapInternal(handler,url);
    }

    //这里正式包装传进来的handler,这里可以想想有没有更优雅的方式，感觉可以弄个factory
    protected ChannelHandler wrapInternal(ChannelHandler handler, URL url) {
        return new MultiMessageHandler(new HeartbeatHandler(new AllChannelHandler(handler,url)));
    }


}
