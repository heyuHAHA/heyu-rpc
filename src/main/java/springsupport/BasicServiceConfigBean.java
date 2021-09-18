package springsupport;

import cn.hutool.core.util.StrUtil;
import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

public class BasicServiceConfigBean extends BasicServiceInterfaceConfig implements BeanNameAware, BeanFactoryAware, InitializingBean {

    private String registryName;

    private String protocolName;

    BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String s) {
        setId(s);
        MotanNamespaceHandler.basicServiceConfigDefineNames.add(s);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String export = getExport();
        String[] protocol_port = export.split(":");
        String protocol = StrUtil.isNotBlank(protocol_port[0]) ? protocol_port[0] : "motan";
        setProtocol(getProtocol(protocol,beanFactory));

        setRegistryConfig(getRegistry(registryName,beanFactory));
    }

    private RegistryConfig getRegistry(String registryName, BeanFactory factory) {
        if (StrUtil.isNotBlank(registryName)) {
            RegistryConfig registryConfig = factory.getBean(registryName,RegistryConfig.class);
            return registryConfig;
        }
        return null;

    }

    private ProtocolConfig getProtocol(String protocol, BeanFactory factory) {
        ProtocolConfig protocolConfig = factory.getBean(protocol, ProtocolConfig.class);
        return protocolConfig;
    }

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }


}
