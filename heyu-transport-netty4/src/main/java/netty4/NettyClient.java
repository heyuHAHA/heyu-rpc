package netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import rpc.Request;
import rpc.Response;
import rpc.URL;
import transport.AbstractSharedPoolClient;
import transport.TransportException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class NettyClient extends AbstractSharedPoolClient {

    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap;
    private ScheduledFuture<?> timeMonitorFuture = null;

    /**
     * 回收过期任务
     * @param url
     */
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    //TODO
//    protected ConcurrentHashMap<Long,Respo>



    public NettyClient(URL url) {
        super(url);

    }

    @Override
    public Response request(Request request) throws TransportException {
        return null;
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public void close(int timeout) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public URL getUrl() {
        return null;
    }
}

class TimeoutMonitor implements Runnable {
    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();

    }
}
