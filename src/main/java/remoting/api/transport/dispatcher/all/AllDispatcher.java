package remoting.api.transport.dispatcher.all;

import common.URL;
import remoting.api.ChannelHandler;
import remoting.api.Dispatcher;

public class AllDispatcher implements Dispatcher {
    @Override
    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new AllChannelHandler(handler,url);
    }
}
