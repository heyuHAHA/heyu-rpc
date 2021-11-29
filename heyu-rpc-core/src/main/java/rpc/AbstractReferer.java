package rpc;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractReferer<T> extends AbstractNode implements Referer<T> {

    protected Class<T> clz;
    protected URL serviceUrl;
    protected AtomicInteger activeRefererCount = new AtomicInteger(0);

    public AbstractReferer(Class<T> clz, URL url) {
        super(url);
        this.clz = clz;
        this.serviceUrl = url;
    }

    public AbstractReferer(Class<T> clz, URL url, URL serviceUrl) {
        super(url);
        this.clz = clz;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public Response call(Request request) {
        incrActiveCount(request);
        Response response = null;
        try {
            response = doCall(request);
            return response;
        } finally {
            decrActiveCount(request,response);
        }
    }

    protected abstract Response doCall(Request request);

    protected void decrActiveCount(Request request, Response response) {
        activeRefererCount.decrementAndGet();
    }

    protected void incrActiveCount(Request request) {
        activeRefererCount.incrementAndGet();
    }
}
