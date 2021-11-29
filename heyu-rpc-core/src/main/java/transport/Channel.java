package transport;

import rpc.Request;
import rpc.Response;
import rpc.URL;

import java.net.InetSocketAddress;

public interface Channel {

    /**
     * get Local socket address
     * @return local address
     */
    InetSocketAddress getLocalAddress();

    /**
     * get remote socket address
     * @return
     */
    InetSocketAddress getRemoteAddress();

    Response request(Request request) throws TransportException;


    boolean open();

    void close();

    void close(int timeout);

    boolean isClosed();

    boolean isAvailable();

    URL getUrl();
}
