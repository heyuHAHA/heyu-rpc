package transport;

import executor.DefaultThreadFactory;
import executor.StandardThreadExecutor;
import rpc.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class AbstractSharedPoolClient extends AbstractClient{
    //自定义的一个线程池，多了一些参数控制
    private static final ThreadPoolExecutor EXECUTOR = new StandardThreadExecutor(1, 300, 20000,
            new DefaultThreadFactory("AbstractPoolClient-initPool-", true));

    private final AtomicInteger idx = new AtomicInteger();
//    这里将创建Netty相关的组件的功能弄成工厂类
//    相当于AbstractSharedPoolClient 依赖 SharedObjectFactory, 具体的子类也依赖 ShardObjectFactory
//    这一层工厂是抽象出来的，以后可以实现除了Netty以外的实现（我猜）？
    protected SharedObjectFactory factory;
    //该抽象类持有 Channel的数据结构
    protected ArrayList<Channel> channels;
    //关于连接数的计数
    protected int connections;

    public AbstractSharedPoolClient(URL url) {
        super(url);
        //TODO
        connections = 2;
        for (int i = 0 ; i < connections; i++) {
            channels.add((Channel) factory.makeObject());
        }
        initConnections("asyncInitConnection",false);
    }

    protected void initConnections(String asyncInitConnection, boolean async) {
        /**
         * 异步则交给线程池排队处理, 否则直接创建连接
         */
        if (async) {
            EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    createConnections();
                }
            });
        } else {
            createConnections();
        }
    }

    protected  void createConnections(){
        for (Channel channel : channels) {
            /**
             * 这里try catch是因为允许client连接失败，但是要记录日志
             */
            try {
                channel.open();
            } catch (Exception e) {

            }
        }
    }

    protected void closeAllChannels() {
        if (channels != null && channels.size() > 0) {
            for (Channel channel : channels) {
                channel.close();
            }
        }
    }
}
