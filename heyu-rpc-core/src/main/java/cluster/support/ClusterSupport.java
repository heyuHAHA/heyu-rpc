package cluster.support;

import cluster.Cluster;
import cluster.HaStrategy;
import cluster.LoadBalance;
import cluster.ha.FailoverHaStrategy;
import cluster.loadbalance.ActiveWeightLoadBalance;
import rpc.Protocol;
import rpc.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ClusterSupport<T> {
    private static ConcurrentHashMap<String, Protocol> protocols = new ConcurrentHashMap<>();
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static Set<ClusterSupport> refreshSet = new HashSet<>();

    private Class<T> interfaceClass;
    private List<URL> registryUrls;
    private URL url;
    private Cluster<T> cluster;

    static  {

    }

    public ClusterSupport(Class<T> interfaceClass, List<URL> registryUrls, URL refUrl) {
        this.interfaceClass = interfaceClass;
        this.registryUrls = registryUrls;
        this.url = refUrl;
    }

    public void init() {
        long start = System.currentTimeMillis();

        //设置集群的负载均衡方法，失败策略，集群名
        prepareCluster();
        //创建一个referUrl的copy，是service url
        URL subUrl = toSubscribeUrl(url);
        for (URL ru : registryUrls) {
            String
        }
    }

    private URL toSubscribeUrl(URL url) {
        URL subUrl = url.createCopy();
        subUrl.addParameters("nodeType","service");
        return subUrl;
    }

    private void prepareCluster() {
        String clusterName = "default";
        String loadBalanceName = "activeWeight";
        String hasStrategyName = "failover";

        //扩展点
        cluster = new ClusterApi();
        LoadBalance<T> loadBalance = new ActiveWeightLoadBalance();
        HaStrategy<T> ha = new FailoverHaStrategy<>();
        ha.setUrl(url);

        cluster.setLoadBalance(loadBalance);
        cluster.setUrl(url);
        cluster.setHaStrategy(ha);


    }
}
