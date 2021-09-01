package remoting.api.transport.dispatcher.all;

import common.URL;
import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.RemotingException;
import remoting.api.transport.ChannelHandlerDelegate;
import remoting.api.transport.dispatcher.ChannelEventRunnable;

import java.util.concurrent.ExecutorService;

public class AllChannelHandler implements ChannelHandlerDelegate {


    protected final ChannelHandler handler;
    protected  final URL url;

    public AllChannelHandler(ChannelHandler channelHandler, URL url) {
        this.handler = channelHandler;
        this.url = url;
    }
    @Override
    public void connected(Channel channel) throws RemotingException {
        //获取线程池
        ExecutorService excutor = getExecutorService();
        try {
            excutor.execute(new ChannelEventRunnable(channel,handler, ChannelEventRunnable.ChannelState.CONNECTED));
        } catch (Throwable throwable) {

        }
    }

    //TODO 获取线程池
    private ExecutorService getExecutorService() {
        return null;
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        ExecutorService executor = getExecutorService();
        executor.execute(new ChannelEventRunnable(channel,handler, ChannelEventRunnable.ChannelState.DISCONNECTED));
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {

    }

    @Override
    //客户端执行的方法
    public void received(Channel channel, Object message) throws RemotingException {
        ExecutorService executor = getExecutorService();
        try {
            executor.execute(new ChannelEventRunnable(channel,handler, ChannelEventRunnable.ChannelState.RECEIVED));
        } catch (Throwable t) {

        }
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        ExecutorService executor = getExecutorService();
        executor.execute(new ChannelEventRunnable(channel,handler, ChannelEventRunnable.ChannelState.CAUGHT));
    }

    @Override
    public ChannelHandler getHandler() {
        if(handler instanceof ChannelHandlerDelegate) {
            return ((ChannelHandlerDelegate) handler).getHandler();
        } else {
            return handler;
        }
    }
}
