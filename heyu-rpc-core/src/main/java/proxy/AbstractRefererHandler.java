package proxy;

import cluster.Cluster;
import rpc.Request;

import java.util.List;

public class AbstractRefererHandler<T> {
    protected List<Cluster<T>> clusters;
    protected Class<T> clz;
    protected String interfaceName;

    Object invokeRequest(Request request, Class returnType, boolean async ) throws Throwable {
        return null;
    }

}
