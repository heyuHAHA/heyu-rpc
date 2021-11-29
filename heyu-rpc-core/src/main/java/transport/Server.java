package transport;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface Server extends EndPoint{

    boolean isBound();

    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress inetSocketAddress);
}
