package springsupport;

import config.basic.BasicRefererInterfaceConfig;
import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class RefererConfigBean<T> implements FactoryBean<T>, BeanFactoryAware, InitializingBean, DisposableBean {
    protected  ProtocolConfig protocolConfig;

    protected BasicRefererInterfaceConfig basicRefererInterfaceConfig;

    protected RegistryConfig registryConfig;

    private transient BeanFactory beanFactory;

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
}
