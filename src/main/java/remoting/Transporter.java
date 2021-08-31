package remoting;

import common.URL;

public interface Transporter {
    RemotingServer bind(URL url, ChannelHandler handler) throws RemotingException;

    Client connect(URL url, ChannelHandler channelHandler) throws RemotingException;
}
