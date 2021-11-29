package registry;

import rpc.URL;

import java.util.List;

public interface DiscoveryService {
    void subscribe(URL url, NotifyListener listener);

    void unsubscribe(URL url, NotifyListener listener);

    List<URL> discover(URL url);
}
