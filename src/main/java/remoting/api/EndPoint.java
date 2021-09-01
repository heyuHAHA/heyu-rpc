package remoting.api;

import common.URL;

import java.net.InetSocketAddress;

public interface EndPoint {

    URL getUrl();

    InetSocketAddress getLocalAddress();

    void send(Object message) throws RemotingException;

    void send(Object message, boolean sent) throws RemotingException;

    void close();

    boolean isClosed();
}
