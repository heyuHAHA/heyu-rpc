package remoting.api;

/**
 * 指示实现（对于服务器和客户端）是否具有感知和处理空闲连接的能力。
 *   * 如果服务器有能力处理空闲连接，它应该在发生时关闭连接，如果
 *   * 客户端有能力处理空闲连接，它应该向服务器发送心跳。
 */
public interface IdleSensible {



    default boolean canHandleIdle() {
        return false;
    }
}
