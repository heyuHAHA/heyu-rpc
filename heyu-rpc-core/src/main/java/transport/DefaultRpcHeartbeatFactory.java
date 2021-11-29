package transport;

import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import rpc.DefaultRequest;
import rpc.DefaultResponse;
import rpc.Request;
import rpc.Response;
import util.RequestIdGenerator;

@Slf4j
public class DefaultRpcHeartbeatFactory implements HeartbeatFactory{

    public static final HeartbeatFactory HEARTBEAT_FACTORY = new DefaultRpcHeartbeatFactory();
    private static final Logger logger = Logger.getLogger(DefaultRpcHeartbeatFactory.class);

    private DefaultRpcHeartbeatFactory() {
        logger.info("create a single DefaultRpcHeartbeatFactory");
    }

    @Override
    public Request createRequest() {
        return getDefaultHeartbeatRequest(RequestIdGenerator.getRequestId());
    }

    private Request getDefaultHeartbeatRequest(long requestId) {
        HeartbeatRequest request = new HeartbeatRequest();
        request.setRequestId(requestId);
        return request;
    }

    @Override
    public MessageHandler wrapMessageHandler(MessageHandler handler) {
        return null;
    }

    public static boolean isHeartbeatRequest(Object message) {
        //这里有点像写equals的套路
        if (!(message instanceof Request))
            return false;
        if (message instanceof HeartbeatRequest) {
            return true;
        }

        Request request = (Request)  message;
        //TODO
        return  true;
    }

    private class HeartMessageHandleWrapper implements MessageHandler {

        private MessageHandler messageHandler;

        public HeartMessageHandleWrapper(MessageHandler messageHandler) {
            this.messageHandler = messageHandler;
        }

        @Override
        public Object handle(Channel channel, Object message) {
            if (isHeartbeatRequest(message)) {
                Response response = getDefaultHeartbeatResponse(((Request) message).getRequestId());
                return response;
            }
            return messageHandler.handle(channel,message);
        }
    }

    public static Response getDefaultHeartbeatResponse(long requestId) {
        HeartbeatResponse response = new HeartbeatResponse();
        response.setRequestId(requestId);
        response.setValue("heartbeat");
        return response;
    }

    static class HeartbeatRequest extends DefaultRequest{}
    static class HeartbeatResponse extends DefaultResponse {}
}
