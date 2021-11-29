package registry;

import rpc.URL;

public interface Registry extends RegistryService,DiscoveryService{
    URL getUrl();
}
