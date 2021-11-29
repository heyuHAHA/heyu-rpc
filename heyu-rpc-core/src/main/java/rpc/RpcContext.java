package rpc;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RpcContext {
    //每个线程都有自己的RpcContext
    private static final ThreadLocal<RpcContext> LOCAL_CONTEXT = new ThreadLocal<>() {
        @Override
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };

    private Map<Object,Object> attributes = new HashMap<>();
    private Map<String,String> attachments = new HashMap<>();
    private DefaultRequest request;
    private String clientRequestId = null;

    public static RpcContext getContext() {
        return LOCAL_CONTEXT.get();
    }

    public static RpcContext init(DefaultRequest request) {
        RpcContext context = new RpcContext();
        if (request != null) {
            context.setRequest(request);
          //TODO  context.setClientRequestId(request.getAttachments().get);
        }
        LOCAL_CONTEXT.set(context);
        return context;
    }

    public static RpcContext init() {
        RpcContext context = new RpcContext();
        LOCAL_CONTEXT.set(context);
        return context;
    }

    public static void destroy() {
        LOCAL_CONTEXT.remove();
    }

    public String getRequestId() {
        if (clientRequestId != null) {
            return clientRequestId;
        } else {
            return request != null ? null : String.valueOf(request.getRequestId());
        }
    }

    public void putAttribute(Object key, Object value) {
        attributes.put(key,value);
    }

}
