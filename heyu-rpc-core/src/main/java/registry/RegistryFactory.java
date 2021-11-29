package registry;

import rpc.URL;

public interface RegistryFactory {
    Registry getRegistry(URL url);
}
