package transport;

import codec.Codec;
import exception.HeyuRpcFrameworkException;


import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Collection;

public abstract class AbstractServer implements Server{
    //最基础的就是封装ip地址，本地的和远程的
    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;

    protected URL url;
    protected Codec codec;

    public AbstractServer(){}

    protected volatile ChannelState state = ChannelState.UNINIT;

    public static enum ChannelState {

        /** 未初始化状态 **/
        UNINIT(0),
        /** 初始化完成 **/
        INIT(1),
        /** 存活可用状态 **/
        ALIVE(2),
        /** 不存活可用状态 **/
        UNALIVE(3),
        /** 关闭状态 **/
        CLOSE(4);

        public final int value;
        private ChannelState(int value){
            this.value = value;
        }
        public boolean isAliveState() {
            return this == ALIVE;
        }

        public boolean isUnAliveState() {
            return this == UNALIVE;
        }

        public boolean isCloseState() {
            return this == CLOSE;
        }

        public boolean isInitState() {
            return this == INIT;
        }

        public boolean isUnInitState() {
            return this == UNINIT;
        }
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public Collection<Channel> getChannels() {
        throw new HeyuRpcFrameworkException(this.getClass().getName() + "getChannels() method unsupport");
    }

    @Override
    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        throw new HeyuRpcFrameworkException(this.getClass().getName() + "getChannel(InetSocketAddress) method unsupport");
    }
}
