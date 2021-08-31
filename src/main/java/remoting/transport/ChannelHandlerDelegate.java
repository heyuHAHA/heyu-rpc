package remoting.transport;

import remoting.ChannelHandler;

public interface ChannelHandlerDelegate extends ChannelHandler {
    ChannelHandler getHandler();
}
