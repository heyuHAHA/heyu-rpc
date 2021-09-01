package remoting.api;

import common.URL;

public interface Dispatcher {

    /**
     * dispatch the message to threadpool
     * @param handler
     * @param url
     * @return
     */
    ChannelHandler dispatch(ChannelHandler handler, URL url);
}
