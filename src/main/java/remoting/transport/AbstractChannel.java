package remoting.transport;

import common.URL;
import remoting.Channel;
import remoting.ChannelHandler;
import remoting.RemotingException;

import java.net.InetSocketAddress;

/**
 * 具备Channel的属性管理，以及连接，发送，断开，接收等功能
 */
public abstract class AbstractChannel extends AbstractPeer implements Channel {
    public AbstractChannel(URL url, ChannelHandler handler) {
        super(url, handler);
    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        if(isClosed())
            throw new RemotingException(this,"Failed to send message");
        doSend(message,sent);
    }

    protected abstract void doSend(Object message, boolean sent);
}
