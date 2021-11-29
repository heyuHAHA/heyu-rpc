package cluster.support;

import cluster.Cluster;
import cluster.HaStrategy;
import cluster.LoadBalance;
import rpc.Request;
import rpc.Response;
import rpc.URL;

public class ClusterApi<T> implements Cluster<T> {

    private HaStrategy<T> haStrategy;

    private LoadBalance<T> loadBalance;

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public String desc() {
        return null;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public void setUrl(URL url) {

    }

    @Override
    public void setLoadBalance(LoadBalance loadBalance) {

    }


    @Override
    public void setHaStrategy(HaStrategy haStrategy) {

    }

    @Override
    public Response call(Request request) {
        try {
            return haStrategy.call(request,loadBalance);
        } catch (Exception e) {
            return callFalse(request,e);
        }
    }

    protected Response callFalse(Request request, Exception cause) {
        return null;
    }
}
