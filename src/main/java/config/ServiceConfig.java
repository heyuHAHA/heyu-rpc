package config;

import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import org.springframework.beans.factory.BeanFactory;
import springsupport.BasicServiceInterfaceConfig;

public class ServiceConfig<T>{
    private Class<T> interfaceClass;

    private BeanFactory beanFactory;

    private BasicServiceInterfaceConfig basicService;

    private String export;

    private String host;

    private ProtocolConfig protocolConfig;

    private String application;

    private String module;

    private String group;

    private String version;

    private RegistryConfig registryConfig;
    // 接口实现类引用
    private T ref;

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public void setInterface(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setBeanFactory(BeanFactory factory) {
        this.beanFactory = factory;
    }

    public void setBasicService(BasicServiceInterfaceConfig bean) {
        this.basicService = bean;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setProtocol(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
    }

    public void setApplication(String application) {
        this.application = application;
    }


    public void setModule(String module) {
        this.module = module;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public BasicServiceInterfaceConfig getBasicService() {
        return basicService;
    }

    public String getExport() {
        return export;
    }

    public String getHost() {
        return host;
    }

    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }

    public String getApplication() {
        return application;
    }

    public String getModule() {
        return module;
    }

    public String getGroup() {
        return group;
    }

    public String getVersion() {
        return version;
    }

    public void export() {
    }
}
