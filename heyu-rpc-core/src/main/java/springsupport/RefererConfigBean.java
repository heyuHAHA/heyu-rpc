package springsupport;

import cluster.Cluster;
import cluster.support.ClusterSupport;
import cn.hutool.core.util.StrUtil;
import config.basic.BasicRefererInterfaceConfig;
import config.handler.SimpleConfigHandler;
import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import registry.RegistryService;
import rpc.URL;
import util.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class RefererConfigBean<T> implements FactoryBean<T>, BeanFactoryAware, InitializingBean, DisposableBean {
    private static Logger logger = Logger.getLogger(RefererConfigBean.class);
    protected  ProtocolConfig protocolConfig;

    protected BasicRefererInterfaceConfig basicRefererInterfaceConfig;

    protected RegistryConfig registryConfig;

    private transient BeanFactory beanFactory;

    private Class<T> interfaceClass;

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private List<ClusterSupport> clusterSupports;

    //解析后的所有注册中心url
    private List<URL> registryUrls = new ArrayList<>();
    //注册中心的配置列表
    private List<RegistryConfig> registries;


    private List<ProtocolConfig> protocols;

    private T ref;

    public T getRef() {
        if (ref == null) {
            initRef();
        }
        return ref;
    }

    public synchronized void initRef() {
        if (initialized.get()) {
            return;
        }
        try {
            interfaceClass = (Class<T>) Class.forName(interfaceClass.getName(),true,Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //检查类对象和配置的方法是否一致
        //checkInterfactAndMethods(xxx,xxx)

        clusterSupports = new ArrayList<>(protocols.size());
        List<Cluster<T>> clusters = new ArrayList<>(protocols.size());
        String proxy = null;
        SimpleConfigHandler configHandler = new SimpleConfigHandler();

        //生成注册中心的url到List<URL>中
        loadRegistryUrls();

        String proxyType = null;

        //生成RPC URL

//       String localIp = getLocalHostAddress();
        for (ProtocolConfig protocol : protocols) {
            Map<String,String> params = new HashMap<>();
            params.put("nodeType","referer");
            params.put("version","default");
            params.put("refreshTimestamp",String.valueOf(System.currentTimeMillis()));

            //组装参数 TODO
//            collectConfigParams(params, protocol, basicReferer, extConfig, this);
//            collectMethodConfigParams(params, this.getMethods());

            String path = interfaceClass.getName();
           // URL refUrl = new URL(protocol.getName(),localIp,0,path,params);
        }
        ref = configHandler.refer(interfaceClass,null,proxyType);

    }

    private void loadRegistryUrls() {
        registryUrls.clear();
        if (registries != null && !registries.isEmpty()) {
            for (RegistryConfig config : registries) {
                String address = config.getAddress();
                if (StrUtil.isBlank(address)) {
                    address = "127.0.0.1" + ":" + 0;
                }
                Map<String,String> map = new HashMap<>();
                config.appendConfigParams(map);

                map.put("path", RegistryService.class.getName());
                map.put("refreshTimestamp",String.valueOf(System.currentTimeMillis()));

                if (!map.containsKey("")) {
                    logger.error("Found no RPC protocol when loadRegistryUrl : " + this.getClass().getSimpleName() + " : loadRegistryUrls" );
                }
                List<URL> urls = UrlUtils.parseURLs(address,map);
                if (urls != null && !urls.isEmpty()) {
                    for (URL url : urls) {
                        registryUrls.add(url);
                    }
                }


            }
        }

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkAndConfigBasicConfig();
        checkAndConfigProtocols();
        checkAndConfigRegistry();
    }

    private void checkAndConfigRegistry() {
        if (getRegistryConfig() == null && getBasicRefererInterfaceConfig() != null
            && getBasicRefererInterfaceConfig().getRegistryConfig() != null) {
            setRegistryConfig(getBasicRefererInterfaceConfig().getRegistryConfig());
        }
        if (getRegistryConfig() == null) {
            for (String name : MotanNamespaceHandler.registryDefineNames) {
                RegistryConfig registryConfig = beanFactory.getBean(name,RegistryConfig.class);
                if (registryConfig == null)
                    continue;
                if (MotanNamespaceHandler.registryDefineNames.size() == 1) {
                    setRegistryConfig(registryConfig);
                } else if (registryConfig.isDefault() != null && registryConfig.isDefault().booleanValue()) {
                    setRegistryConfig(registryConfig);
                }
            }

        }

        if (getRegistryConfig() == null) {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setRegProtocol("local");
            setRegistryConfig(registryConfig);
        }
    }

    private void checkAndConfigProtocols() {
        if (getProtocolConfig() == null && getBasicRefererInterfaceConfig() != null) {
            setProtocolConfig(getBasicRefererInterfaceConfig().getProtocolConfig());
        }
        if (getProtocolConfig() == null) {
            for (String name : MotanNamespaceHandler.protocolDefineNames) {
                ProtocolConfig pc = beanFactory.getBean(name,ProtocolConfig.class);
                setProtocolConfig(pc);
            }
        }
        if (getProtocolConfig() == null) {
            ProtocolConfig pc = new ProtocolConfig();
            pc.setId("motan");
            pc.setName("motan");
            setProtocolConfig(pc);
        }
    }

    private void checkAndConfigBasicConfig() {
        if(getBasicRefererInterfaceConfig() == null) {
            if(beanFactory instanceof ListableBeanFactory) {
                ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
                String[] basicRefererConfigNames = listableBeanFactory.getBeanNamesForType(BasicRefererInterfaceConfig.class);
                for (String name : basicRefererConfigNames) {
                    BasicRefererInterfaceConfig basicReferConfig = beanFactory.getBean(name,BasicRefererInterfaceConfig.class);
                    if (basicReferConfig == null)
                        continue;

                    setBasicRefererInterfaceConfig(basicReferConfig);
                }
            }
        }
    }

    protected String group;

    protected String version;

    protected String module;

    protected String application;

    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }

    public void setProtocolConfig(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
    }

    public BasicRefererInterfaceConfig getBasicRefererInterfaceConfig() {
        return basicRefererInterfaceConfig;
    }

    public void setBasicRefererInterfaceConfig(BasicRefererInterfaceConfig basicRefererInterfaceConfig) {
        this.basicRefererInterfaceConfig = basicRefererInterfaceConfig;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setInterface(Class<?> referenceClass) {

    }
}
