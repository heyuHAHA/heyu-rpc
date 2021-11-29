package cluster.loadbalance;

import cluster.LoadBalance;
import exception.HeyuRpcFrameworkException;
import rpc.Referer;
import rpc.Request;

import java.util.List;

public abstract class AbstractLoadBalance<T>  implements LoadBalance<T> {

    public static final int MAX_REFERER_COUNT = 10;

    private List<Referer<T>> referers;

    @Override
    public void selectToHolder(Request request, List<Referer<T>> refersHolder) {
        List<Referer<T>> referers = this.referers;
        if (referers == null) {
            throw new HeyuRpcFrameworkException(this.getClass().getSimpleName() + " No available referers for call request");
        }

        if (referers.size() > 1) {
            doSelectToHolder(request,refersHolder);
        } else if (referers.size() == 1 && referers.get(0).isAvailable()) {
            refersHolder.add(referers.get(0));
        }
        if (referers.isEmpty()) {
            throw new HeyuRpcFrameworkException(this.getClass().getSimpleName() + " No available referers for call : referers_size = " +referers.size());
        }
    }

    protected List<Referer<T>> getReferers() {
        return referers;
    }

    protected abstract void doSelectToHolder(Request request, List<Referer<T>> refersHolder);
}
