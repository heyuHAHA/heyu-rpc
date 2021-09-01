package remoting.api;

import java.net.InetSocketAddress;




/**
 * 将通信的通道抽象为Channel，封装通信的状态，属性
 * 当然，继承了EndPoint，也有发送功能etc
 */
public interface Channel extends EndPoint{
    InetSocketAddress getRemoteAddress();

    boolean isConnected();

    boolean hasAttribute(String key);

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    void removeAttribute(String key);
}
