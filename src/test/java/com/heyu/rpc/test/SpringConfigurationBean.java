package com.heyu.rpc.test;

import com.heyu.rpc.test.springsupport.MotanReferTest;
import com.heyu.rpc.test.springsupport.MyController;
import config.basic.BasicRefererInterfaceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springsupport.AnnotationBean;
import springsupport.BasicRefererConfigBean;
import springsupport.ProtocolConfigBean;
import springsupport.RegistryConfigBean;


@Configuration
public class SpringConfigurationBean {

    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.heyu.rpc.test.springsupport");
        return annotationBean;
    }

    @Bean(name="demoMotan")
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

    @Bean(name="basicRefererConfig")
    public BasicRefererInterfaceConfig basicReferConfig() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setRegistryName("registry");
        config.setProtocolName("demoMotan");
        return config;
    }

    @Bean
    public MyController myController() {
        return new MyController();
    }
}
