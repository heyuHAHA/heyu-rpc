package cluster;

import rpc.Caller;
import rpc.URL;

public interface Cluster<T> extends Caller<T> {
    void setUrl(URL url);

    void setLoadBalance(LoadBalance loadBalance);

    void setHaStrategy(HaStrategy<T> haStrategy);
}
