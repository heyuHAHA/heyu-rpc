package protocol;

import exception.HeyuRpcFrameworkException;
import netty4.NettyEndpointFactory;
import rpc.AbstractReferer;
import rpc.Request;
import rpc.Response;
import rpc.URL;
import transport.Client;
import transport.EndPointFactory;

public class DefaultRpcReferer<T> extends AbstractReferer<T> {

    protected Client client;
    protected EndPointFactory endPointFactory;



    public DefaultRpcReferer(Class<T> clz, URL url, URL serviceUrl) {
        super(clz,url,serviceUrl);

        endPointFactory = new NettyEndpointFactory();
        client = endPointFactory.createClient(url);

    }

    @Override
    protected Response doCall(Request request) {
       try {
           return client.request(request);
       } catch (Exception e) {
           throw new HeyuRpcFrameworkException("DefaultRpcReferer call Error : url = " + url);
       }
    }

    @Override
    public int activeRefererCount() {
        return 0;
    }
}
