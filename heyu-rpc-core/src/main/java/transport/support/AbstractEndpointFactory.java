package transport.support;

import rpc.URL;
import transport.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractEndpointFactory implements EndPointFactory {
    protected Map<String, Server> ipPort2ServerShareChannel = new HashMap<>();
    protected ConcurrentHashMap<Server, Set<String>> server2UrlsShareChannel = new ConcurrentHashMap<>();

    private EndPointManager heartbeatClientEndPointManager = null;

    public AbstractEndpointFactory() {
        heartbeatClientEndPointManager = new HeartbeatClientEndpointManager();
        heartbeatClientEndPointManager.init();
    }

    @Override
    public Server createServer(URL url, MessageHandler messageHandler) {
        messageHandler = getHeartbeatFactory(url).wrapMessageHandler(messageHandler);

        synchronized (ipPort2ServerShareChannel) {
            String inPort = url.getServerPortStr();
            
            Server server = ipPort2ServerShareChannel.get(inPort);
            if (server != null) {
                return server;
            }
            url = url.createCopy();
            
            server = innerCreateServer(url,messageHandler);
            ipPort2ServerShareChannel.put(inPort,server);
            return server;
        }
    }

    protected abstract Server innerCreateServer(URL url, MessageHandler messageHandler);

    private HeartbeatFactory getHeartbeatFactory(URL url) {
        String heartbeatFactoryname = "motan";
        return getHeartbeatFactory(heartbeatFactoryname);
    }

    private HeartbeatFactory getHeartbeatFactory(String heartbeatFactoryname) {
        HeartbeatFactory heartbeatFactory = DefaultRpcHeartbeatFactory.HEARTBEAT_FACTORY;
        return heartbeatFactory;

    }

    @Override
    public Client createClient(URL url) {
        return createClient(url, heartbeatClientEndPointManager);
    }

    private Client createClient(URL url, EndPointManager heartbeatClientEndPointManager) {
        Client client = innerCreateClient(url);

        heartbeatClientEndPointManager.addEndpoint(client);
        return client;
    }

    protected abstract Client innerCreateClient(URL url);

    @Override
    public void safeReleaseResource(Server server, URL url) {

    }

    @Override
    public void safeReleaseResource(Client client, URL url) {

    }
}
