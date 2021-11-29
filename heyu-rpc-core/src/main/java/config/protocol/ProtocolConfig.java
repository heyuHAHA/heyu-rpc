package config.protocol;

import config.AbstractConfig;

public class ProtocolConfig extends AbstractConfig {

    private static final long serialVersionUID = -3079550296015510934L;
    //协议名称
    protected String name;
    //负载均衡方法
    protected String loadbalance;
    //高可用策略
    protected String haStrategy;
    // 最小工作pool线程数
    protected Integer minWorkerThread;
    // 最大工作pool线程数
    protected Integer maxWorkerThread;

    // 采用哪种cluster 的实现
    protected String cluster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public String getHaStrategy() {
        return haStrategy;
    }

    public void setHaStrategy(String haStrategy) {
        this.haStrategy = haStrategy;
    }

    public Integer getMinWorkerThread() {
        return minWorkerThread;
    }

    public void setMinWorkerThread(Integer minWorkerThread) {
        this.minWorkerThread = minWorkerThread;
    }

    public Integer getMaxWorkerThread() {
        return maxWorkerThread;
    }

    public void setMaxWorkerThread(Integer maxWorkerThread) {
        this.maxWorkerThread = maxWorkerThread;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
