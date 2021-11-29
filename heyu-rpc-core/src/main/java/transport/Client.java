package transport;

import rpc.Request;

public interface Client extends EndPoint{

    /**
     * 同步发request
     * @param request
     */
    void heartbeat(Request request);
}
