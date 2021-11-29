package config.handler;

import cluster.Cluster;
import cluster.support.ClusterSupport;
import rpc.Exporter;
import rpc.URL;

import java.util.Collection;
import java.util.List;

public class SimpleConfigHandler implements ConfigHandler{
    @Override
    public <T> ClusterSupport<T> buildClusterSupport(Class<T> interfaceClass, List<URL> registryUrls, URL refUrl) {
        ClusterSupport<T>  clusterSupport = new ClusterSupport<>(interfaceClass,registryUrls,refUrl);
        clusterSupport.init();

        return clusterSupport;
    }

    @Override
    public <T> T refer(Class<T> interfaceClass, List<Cluster<T>> cluster, String proxyType) {
        return null;
    }

    @Override
    public <T> Exporter<T> export(Class<T> interfaceClass, T ref, List<URL> registryUrls, URL serviceUrl) {
        return null;
    }

    @Override
    public <T> void unexport(List<Exporter<T>> exporters, Collection<URL> registryUrls) {

    }
}
