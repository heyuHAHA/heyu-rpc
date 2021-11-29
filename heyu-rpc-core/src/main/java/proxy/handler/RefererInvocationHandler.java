package proxy.handler;

import cluster.Cluster;
import org.apache.commons.lang3.StringUtils;
import rpc.DefaultRequest;
import rpc.Response;
import rpc.RpcContext;
import util.ReflectUtil;
import util.RequestIdGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


public class RefererInvocationHandler<T> implements InvocationHandler {
    protected List<Cluster<T>> clusters;
    protected Class<T> clz;
    //protected SwitcherService switcherService = null;
    protected String interfaceName;


    public RefererInvocationHandler(Class<T> clz) {
        this(clz,null);
    }

    public RefererInvocationHandler(Class<T> clz, List<Cluster<T>> clusters) {
        this.clz = clz;
        this.clusters = clusters;
        init();
        interfaceName = clz.getName();
    }

    private void init() {
    }

    /**
     * 暂时只支持同步
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO 判断是否是本地方法

        DefaultRequest request = new DefaultRequest();
        request.setRequestId(RequestIdGenerator.getRequestId());
        request.setArguments(args);
        String methodName = method.getName();
        request.setMethodName(methodName);
        request.setParamtersDesc(ReflectUtil.getMethodParamDesc(method));
        request.setInterfaceName(interfaceName);
        
        return invokeRequest(request, method.getReturnType());

    }

    private Object invokeRequest(DefaultRequest request, Class<?> returnType) {
        RpcContext curContext = RpcContext.getContext();
        curContext.putAttribute("Async",false);

        Map<String,String> attachments = curContext.getAttachments();
        if (!attachments.isEmpty()) {
            for (Map.Entry<String,String> entry : request.getAttachments().entrySet()) {
                request.setAttachment(entry.getKey(),entry.getValue());
            }
        }

        // add to attachment if client request id is set
        if (StringUtils.isNoneBlank(curContext.getClientRequestId())) {
            request.setAttachment("requestIdFromClient",curContext.getClientRequestId());
        }

        //TODO 当 referer配置多个protocol的时候
        for (Cluster<T> cluster : clusters) {
            Response response = null;
            try {
                response = cluster.call(request);
            } catch (RuntimeException e) {

            }


        }


    }
}
