package remoting.api.transport;

import common.URL;
import common.threadpool.manager.DefaultExecutorRepository;
import io.netty.util.internal.StringUtil;

import org.apache.log4j.Logger;
import remoting.api.*;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ExecutorService;


public abstract class AbstractServer extends AbstractEndpoint implements RemotingServer {

    protected static final String SERVER_THREAD_POOL_NAME= "DubboServerHandler";
    private static final Logger logger = Logger.getLogger(AbstractServer.class.getClass());
//    ExecutorService executor;
    private InetSocketAddress localAddress;
    private InetSocketAddress bindAddress;
    private int accepts;
    private DefaultExecutorRepository executorRepository = new DefaultExecutorRepository();
    private ExecutorService executor;


    public AbstractServer(URL url, ChannelHandler handler) {
        super(url, handler);
        localAddress = getUrl().toInetSocketAddress();
        //这里留个坑，和dubbo的原实现不一样，dubbo会提供个默认值
        String bindIp = StringUtil.isNullOrEmpty(getUrl().getBindIp())  ? getUrl().getHost() : getUrl().getBindIp();
        int bindPort = getUrl().getBindPort() == null ? getUrl().getPort() : getUrl().getBindPort();
        //TODO 这里dubbo判断ip是否是0.0.0.0 或者 ip是不是空，是不是127开头这样，我这里暂时不做判断
//        if (url.getParameter(ANYHOST_KEY, false) || NetUtils.isInvalidLocalHost(bindIp)) {
//            bindIp = ANYHOST_VALUE;
//        }
        bindAddress = new InetSocketAddress(bindIp,bindPort);
        this.accepts = url.getAccepts();
        try {
            //留给不同的服务器实现
            doOpen();
        } catch (Throwable t) {

        }
        //TODO
        executor = executorRepository.createExecutorIfAbsent(url);

    }

    protected abstract void doOpen();

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        Collection<Channel> channels = getChannels();
        for (Channel channel :channels) {
            if (channel.isConnected()) {
                channel.send(message);
            }
        }
    }

    @Override
    public void reset(Object parameters) {

    }


    @Override
    public void close() {
        //TODO log info
        //这里dubbo对关闭线程池做了很多额外功能，可以参考
        executor.shutdownNow();
        //将父类的closed字段set成true
        super.close();
        //留给子类实现
        doClose();
    }

    protected abstract void doClose();

    @Override
    public void connected(Channel channel) throws RemotingException {
        if (this.isClosing() || this.isClosed()) {
            channel.close();
            return;
        }

        if(accepts > 0 && getChannels().size() >accepts) {
            logger.error("AbstractServer accepts > Channel size");
            channel.close();
            return;
        }
        super.connected(channel);
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        Collection<Channel> channels = getChannels();
        //感觉有点多余
        if (channels.isEmpty()) {
            logger.warn("All clients has disconnected from:" + channel.getLocalAddress() );
        }
        super.disconnected(channel);
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public InetSocketAddress getBindAddress() {
        return bindAddress;
    }

    public int getAccepts() {
        return accepts;
    }


    @Override
    protected Codec getCodecByCodecName(String codecName) {
        return null;
    }
}
