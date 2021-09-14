package com.heyu.rpc.test;

import config.basic.BasicRefererInterfaceConfig;
import org.junit.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springsupport.AnnotationBean;
import springsupport.ProtocolConfigBean;
import springsupport.RegistryConfigBean;

@Configuration
public class SpringConfigurationBean {

    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.heyu.rpc.test");
        return annotationBean;
    }

    @Bean(name="motan")
    public ProtocolConfigBean protocolConfig1() {
        ProtocolConfigBean protocolConfigBean = new ProtocolConfigBean();
        protocolConfigBean.setName("motan");
        return protocolConfigBean;
    }

    @Bean(name="registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean registryConfig = new RegistryConfigBean();
        registryConfig.setRegProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1:8300");
        return registryConfig;
    }

//    @Bean(name="basicRefererConfig")
//    public BasicRefererInterfaceConfig basicReferConfig() {
//        BasicRefererInterfaceConfig config = new BasicRefererInterfaceConfig();
//        config.setRegistryConfig();
//    }
}
