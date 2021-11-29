package registry;

import rpc.URL;

import java.util.Collection;

public interface RegistryService {

    void register(URL url);

    void unregister(URL url);

    void available(URL url);

    void unavailable(URL url);

    Collection<URL> getRegisteredServiceUrls();
}
