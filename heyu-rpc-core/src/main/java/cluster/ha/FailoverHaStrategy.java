package cluster.ha;

import cluster.HaStrategy;
import cluster.LoadBalance;
import exception.HeyuRpcFrameworkException;
import rpc.Referer;
import rpc.Request;
import rpc.Response;
import rpc.URL;

import java.util.ArrayList;
import java.util.List;

public class FailoverHaStrategy<T> implements HaStrategy<T> {

    protected ThreadLocal<List<Referer<T>>> referersHolder = new ThreadLocal<>() {
        @Override
        protected List<Referer<T>> initialValue() {
            return new ArrayList<Referer<T>>();
        }
    };

    @Override
    public void setUrl(URL url) {

    }

    @Override
    public Response call(Request request, LoadBalance loadBalance) {
        List<Referer<T>> referers = selectReferes(request,loadBalance);
        if (referers.isEmpty()) {
            throw new HeyuRpcFrameworkException(String.format("FailoverHaStrategy No referers for request:%s"));
        }
        URL url = referers.get(0).getUrl();
        //先使用method的配置
        int tryCount = 3;
        for (int i = 0; i <= tryCount; i++) {
            Referer<T> referer = referers.get(i % referers.size());
            try {
                return referer.call(request);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        throw new HeyuRpcFrameworkException("FailoverHaStrategy should not come here");
    }

    protected List<Referer<T>> selectReferes(Request request, LoadBalance loadBalance) {
        List<Referer<T>> referers = referersHolder.get();
        referers.clear();;
        //每次都重新
        loadBalance.selectToHolder(request,referers);

    }
}
