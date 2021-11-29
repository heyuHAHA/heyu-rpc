package transport;

import rpc.Request;

public interface HeartbeatFactory {
    /**
     * 创建心跳包
     * @return
     */
    Request createRequest();

    /**
     * 包装handler,支持心跳包的处理
     * @param handler
     * @return
     */
    MessageHandler wrapMessageHandler(MessageHandler handler);
}
