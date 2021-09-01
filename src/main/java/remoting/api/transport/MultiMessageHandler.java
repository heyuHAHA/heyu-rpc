package remoting.api.transport;

import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.RemotingException;

public class MultiMessageHandler implements ChannelHandler {

    private ChannelHandler channelHandler;

    public MultiMessageHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {

    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {

    }
}
