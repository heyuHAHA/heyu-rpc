package cluster;

import rpc.Referer;
import rpc.Request;

import java.util.List;

public interface LoadBalance<T> {

    void selectToHolder(Request request, List<Referer<T>> refersHolder);
}
