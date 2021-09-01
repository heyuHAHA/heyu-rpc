package remoting.api.transport;

import remoting.api.ChannelHandler;

public interface ChannelHandlerDelegate extends ChannelHandler {
    ChannelHandler getHandler();
}
