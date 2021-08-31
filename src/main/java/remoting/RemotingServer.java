package remoting;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface RemotingServer extends EndPoint,IdleSensible {

    void reset(Object parameters);

    boolean isBound();

    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress inetSocketAddress);
}
