package springsupport;

import cn.hutool.core.util.StrUtil;

import config.RpcFrameworkException;
import config.ServiceConfig;
import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;

public class ServiceConfigBean<T> extends ServiceConfig<T> implements BeanFactoryAware, InitializingBean, DisposableBean, ApplicationListener<ContextRefreshedEvent> {
    private BeanFactory beanFactory;


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkAndConfigBasicConfig();
        checkAndConfigExport();
        checkAndConfigRegistry();
    }

    private void checkAndConfigRegistry() {
        if (getRegistryConfig() == null && getBasicService() != null) {
            RegistryConfig registryConfig = getBasicService().getRegistryConfig();
            setRegistryConfig(registryConfig);
        }
        if (getRegistryConfig() == null) {
            for (String name :MotanNamespaceHandler.registryDefineNames) {
                RegistryConfig registryConfig = beanFactory.getBean(name,RegistryConfig.class);
                if (registryConfig == null)
                    continue;
                setRegistryConfig(registryConfig);
            }
        }
        if (getRegistryConfig() == null) {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setRegProtocol("local");
            setRegistryConfig(registryConfig);
        }
    }

    private void checkAndConfigExport() {
        //如果export为空，但有basicService，就用BasicService
        if (StringUtil.isNullOrEmpty(getExport()) && getBasicService() != null) {
            setExport(getBasicService().getExport());
        }
        if (StrUtil.isEmpty(getBasicService().getProtocol()) && StrUtil.isNotEmpty(getExport())) {
            String[] export = getExport().split(":");
            if (export.length == 2) {
                String protocolName = export[0];
                ProtocolConfig protocolConfig = beanFactory.getBean(protocolName,ProtocolConfig.class);
                if (protocolConfig == null) {
                    protocolConfig = new ProtocolConfig();
                    protocolConfig.setName("motan");
                    protocolConfig.setId("motan");

                }
                setProtocol(protocolConfig);
            } else {
                throw new RpcFrameworkException("ServiceConfig 's config must be correct for export value :["  + getExport() +"]" );
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory factory) {
        this.beanFactory = factory;
    }

    private void checkAndConfigBasicConfig() {
        if (getBasicService() == null) {
            if (MotanNamespaceHandler.basicServiceConfigDefineNames.size() == 0) {
                if (beanFactory instanceof ListableBeanFactory) {
                    ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
                    String[] beanNames = listableBeanFactory.getBeanNamesForType(BasicServiceInterfaceConfig.class);
                    MotanNamespaceHandler.basicServiceConfigDefineNames.addAll(Arrays.asList(beanNames));
                }
            }

            for (String name :MotanNamespaceHandler.basicServiceConfigDefineNames) {
                BasicServiceInterfaceConfig basicServiceInterfaceConfig = beanFactory.getBean(name, BasicServiceInterfaceConfig.class);
                if (basicServiceInterfaceConfig == null)
                    continue;
                if (MotanNamespaceHandler.basicServiceConfigDefineNames.size() == 1) {
                    setBasicService(basicServiceInterfaceConfig);
                }else if (basicServiceInterfaceConfig.isDefault()) {
                    setBasicService(basicServiceInterfaceConfig);
                }
            }


        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
