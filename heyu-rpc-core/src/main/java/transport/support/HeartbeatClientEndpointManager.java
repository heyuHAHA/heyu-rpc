package transport.support;

import transport.Client;
import transport.EndPoint;
import transport.EndPointManager;
import transport.HeartbeatFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatClientEndpointManager implements EndPointManager {

    private ConcurrentHashMap<Client, HeartbeatFactory> endpoints = new ConcurrentHashMap<>();

    private ScheduledExecutorService executorService = null;

    @Override
    public void init() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Client,HeartbeatFactory> entry : endpoints.entrySet()) {
                   Client endpoint = entry.getKey();

                   try {
                       //如果节点是存活状态，那么没必要走心跳
                       if (endpoint.isAvailable()) {
                           continue;
                       }
                       HeartbeatFactory factory = entry.getValue();
                       endpoint.heartbeat(factory.createRequest());
                   } catch (Exception e) {

                   }
                }
            }
        },500,500, TimeUnit.MILLISECONDS);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void addEndpoint(EndPoint endPoint) {

    }

    @Override
    public void removeEndPoint(EndPoint endPoint) {

    }
}
