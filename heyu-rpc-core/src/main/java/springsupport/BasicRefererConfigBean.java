package springsupport;

import cn.hutool.core.util.StrUtil;
import config.basic.BasicRefererInterfaceConfig;
import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

public class BasicRefererConfigBean extends BasicRefererInterfaceConfig implements BeanNameAware, InitializingBean, BeanFactoryAware {

    private String protocolName;
    private String registryName;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String s) {
        setId(s);
        MotanNamespaceHandler.basicRefererConfigDefineNames.add(s);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRegistryConfig(getRegistry(registryName,beanFactory));
        setProtocolConfig(getProtocol(protocolName,beanFactory));
    }

    private ProtocolConfig getProtocol(String protocolName, BeanFactory factory) {
        if (StrUtil.isNotBlank(protocolName)) {
            ProtocolConfig protocolConfig = factory.getBean(protocolName,ProtocolConfig.class);
            return protocolConfig;
        }
        return null;
    }

    private RegistryConfig getRegistry(String registryName, BeanFactory factory) {
        if (StrUtil.isNotBlank(registryName)) {
            RegistryConfig registryConfig = factory.getBean(registryName,RegistryConfig.class);
            return registryConfig;
        }
        return null;

    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }
}
