package cluster.support;

import rpc.Protocol;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ClusterSupport<T> {
    private static ConcurrentHashMap<String, Protocol> protocols = new ConcurrentHashMap<>();
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static Set<ClusterSupport> refreshSet = new HashSet<>();

    static  {

    }
}
