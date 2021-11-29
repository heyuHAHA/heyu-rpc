package proxy;

import cluster.Cluster;

import java.util.List;

public interface ProxyFactory {

    <T> T getProxy(Class<T> clazz, List<Cluster<T>> clusters);

    <T> T getProxy(Class<T> clazz);
}
