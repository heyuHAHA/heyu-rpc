package registry.support;

import config.RpcFrameworkException;
import registry.Registry;
import registry.RegistryFactory;
import rpc.URL;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractRegistryFactory implements RegistryFactory {
    private static ConcurrentHashMap<String,Registry> registries = new ConcurrentHashMap<>();
    //把lock加上static final是为什么
    private static final ReentrantLock lock = new ReentrantLock();

    public String getRegistryUri(URL url) {
        String registryUri = url.getUri();
        return registryUri;
    }

    @Override
    public Registry getRegistry(URL url) {
        String registryUri = getRegistryUri(url);
        try {
            lock.lock();
            Registry registry = registries.get(url);
            if (registry != null) {
                return  registry;
            }
            registry = createRegistry(url);
            if (registry == null) {
                throw new RpcFrameworkException("Create registry false for url: " + url);
            }
            registries.put(url,registry);
            return registry;
        }catch (Exception e) {
            throw new RpcFrameworkException("Create registry false for url: " + url);
        } finally {
            lock.unlock();
        }
    }

    protected abstract Registry createRegistry(URL url);
}
