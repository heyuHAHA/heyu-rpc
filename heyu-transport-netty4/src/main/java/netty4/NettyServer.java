package netty4;

import rpc.Request;
import rpc.Response;
import rpc.URL;
import transport.AbstractServer;
import transport.MessageHandler;
import transport.Server;
import transport.TransportException;

public class NettyServer  extends AbstractServer  {
    public NettyServer(URL url, MessageHandler messageHandler) {
    }

    @Override
    public Response request(Request request) throws TransportException {
        return null;
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public void close(int timeout) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public boolean isBound() {
        return false;
    }
}
