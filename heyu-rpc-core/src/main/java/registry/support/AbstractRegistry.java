package registry.support;

import cn.hutool.core.collection.ConcurrentHashSet;
import org.apache.log4j.Logger;
import registry.NotifyListener;
import registry.Registry;
import rpc.URL;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistry implements Registry {
    private static final Logger logger = Logger.getLogger(AbstractRegistry.class);
    private ConcurrentHashMap<URL, Map<String, List<URL>>> subscribedCategoryResponse = new ConcurrentHashMap<>();
    private URL registryUrl;
    private Set<URL> registeredServiceUrls = new ConcurrentHashSet<>();
    protected  String registryClassName = this.getClass().getSimpleName();

    public AbstractRegistry(URL url) {
        this.registryUrl = url.createCopy();

    }

    @Override
    public void register(URL url) {
        if (url == null) {
            logger.warn(registryClassName + "register with malformed param, url is null");
            return;
        }
        logger.info(registryClassName + "Url " + url + " will register to Registry");
        doRegister(url.createCopy());
        //缓存
        registeredServiceUrls.add(url);

    }

    protected abstract void doRegister(URL copy);

    @Override
    public void unregister(URL url) {
        if (url == null) {
            logger.warn(registryClassName + " unregister with malformed param, url is null");
            return;
        }
        logger.info(registryClassName + " URL " + url + " will unregister to Registry");
        doUnregister(url.createCopy());
    }

    protected abstract void doUnregister(URL copy);

    @Override
    public void subscribe(URL url, NotifyListener listener) {
        if (url == null || listener == null) {
            logger.warn(registryClassName + " subscribe with malformed param, url " + registryClassName);
            return;
        }
       doSubscribe(url.createCopy(),listener);
    }

    protected abstract void doSubscribe(URL copy, NotifyListener listener);

    @Override
    public void unsubscribe(URL url, NotifyListener listener) {
        if(url == null || listener == null) {
            return;
        }
        doUnsubscribe(url.createCopy(),listener);
    }

    protected abstract void doUnsubscribe(URL copy, NotifyListener listener);

    @Override
    public List<URL> discover(URL url) {
        if(url == null) {
            return Collections.EMPTY_LIST;
        }
        url = url.createCopy();
        List<URL> results = new ArrayList<>();

        Map<String,List<URL>> categoryUrls = subscribedCategoryResponse.get(url);
        if (categoryUrls != null && categoryUrls.size() > 0) {
            for (List<URL> urls : categoryUrls.values()) {
                for (URL tempUrl : urls) {
                    results.add(tempUrl.createCopy());
                }
            }
        } else {
            List<URL> urlsDiscovered = doDiscover(url);
            if (urlsDiscovered != null) {
                for (URL u : urlsDiscovered) {
                    results.add(u);
                }
            }
        }
        return results;
    }

    protected abstract List<URL> doDiscover(URL url);

    @Override
    public Collection<URL> getRegisteredServiceUrls() {
        return registeredServiceUrls;
    }

    @Override
    public URL getUrl() {
        return registryUrl;
    }
}
