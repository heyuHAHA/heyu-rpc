package cluster;

import rpc.Request;
import rpc.Response;
import rpc.URL;

public interface HaStrategy<T> {
    void setUrl(URL url);

    Response call(Request request, LoadBalance<T> loadBalance);

}
