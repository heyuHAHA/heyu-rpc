package netty4;

import rpc.URL;
import transport.Client;
import transport.MessageHandler;
import transport.Server;
import transport.support.AbstractEndpointFactory;

public class NettyEndpointFactory extends AbstractEndpointFactory {
    @Override
    protected Server innerCreateServer(URL url, MessageHandler messageHandler) {
        return new NettyServer(url,messageHandler);
    }

    @Override
    protected Client innerCreateClient(URL url) {
        return new NettyClient(url);
    }
}
