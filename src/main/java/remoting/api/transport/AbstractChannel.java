package remoting.api.transport;

import common.URL;
import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.RemotingException;

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

    //TODO
    protected abstract void doSend(Object message, boolean sent);
}
