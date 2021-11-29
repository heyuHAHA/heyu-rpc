package config.handler;

import cluster.Cluster;
import cluster.support.ClusterSupport;
import rpc.Exporter;
import rpc.URL;

import java.util.Collection;
import java.util.List;

public interface ConfigHandler {
    <T> ClusterSupport<T> buildClusterSupport(Class<T> interfaceClass, List<URL> registryUrls, URL refUrl);

    <T> T refer(Class<T> interfaceClass, List<Cluster<T>> cluster, String proxyType);

    <T> Exporter<T> export(Class<T> interfaceClass, T ref, List<URL> registryUrls, URL serviceUrl);

    <T> void unexport(List<Exporter<T>> exporters, Collection<URL> registryUrls);
}
