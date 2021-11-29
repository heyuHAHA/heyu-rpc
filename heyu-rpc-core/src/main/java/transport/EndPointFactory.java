package transport;

import rpc.URL;

public interface EndPointFactory {

    Server createServer(URL url, MessageHandler messageHandler);

    Client createClient(URL url);

    void safeReleaseResource(Server server, URL url);

    void safeReleaseResource(Client client, URL url);
}
