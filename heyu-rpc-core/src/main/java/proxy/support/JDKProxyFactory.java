package proxy.support;

import cluster.Cluster;
import proxy.ProxyFactory;
import proxy.handler.RefererInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.List;

public class JDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz, List<Cluster<T>> clusters) {
        return null;
    }

    @Override
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new RefererInvocationHandler<>(clazz));
    }
}
