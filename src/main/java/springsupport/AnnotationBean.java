package springsupport;

import cn.hutool.core.collection.ConcurrentHashSet;
import config.ServiceConfig;
import config.basic.BasicRefererInterfaceConfig;
import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.ClassUtils;
import springsupport.annotation.MotanReferer;
import springsupport.annotation.MotanService;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationBean implements BeanFactoryPostProcessor, BeanPostProcessor, BeanFactoryAware {
    private String id;

    private String  annotationPackage;

    private String[] annotationPackages;

    private Map<String,RefererConfigBean> refererConfigBeanMap = new ConcurrentHashMap<>();

    private BeanFactory beanFactory;

    private Set<ServiceConfig> serviceConfigs = new ConcurrentHashSet<>();
    @Override
    /**
     * 把motan指定路径下的class注册到spring容器中
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       if (beanFactory instanceof BeanDefinitionRegistry) {
           try {
               Class<?> scannerClass = ClassUtils.forName("org.springframework.context.annotation.ClassPathBeanDefinitionScanner",AnnotationBean.class.getClassLoader());
               Object scanner = scannerClass.getConstructor(new Class[]{BeanDefinitionRegistry.class,boolean.class})
                       .newInstance(new Object[]{beanFactory,true});

               Method scan = scannerClass.getMethod("scan",new Class[]{String[].class});
               scan.invoke(scanner, new Object[]{annotationPackages});
           } catch (Throwable e) {

           }
       }
    }

    /**
     * 初始化refer方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //排除不是指定路径下的类
       if(!isMatchPackage(bean)) {
           return bean;
       }
       Class<?> clazz = bean.getClass();
       if (isProxyBean(bean))
           clazz = AopUtils.getTargetClass(bean);
       Method[] methods = clazz.getMethods();
       for (Method method : methods) {
           String name = method.getName();
           if (name.length() > 3 && name.startsWith("set")
                   && Modifier.isPublic(method.getModifiers())
                   && method.getParameterTypes().length == 1
                   && !Modifier.isStatic(method.getModifiers())
           ) {
               MotanReferer reference = method.getAnnotation(MotanReferer.class);
               if (reference != null) {
                   Object value = refer(reference,method.getParameterTypes()[0]);
               }
           }
       }
       return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!isMatchPackage(bean)) {
            return bean;
        }
        Class<?> clazz = bean.getClass();
        if (isProxyBean(bean)) {
            clazz = AopUtils.getTargetClass(bean);
        }
        MotanService motanService = bean.getClass().getAnnotation(MotanService.class);
        //获取注解上的属性值
        if(motanService != null) {
            ServiceConfigBean<Object> serviceConfig = new ServiceConfigBean<>();
            if (void.class.equals(motanService.interfaceClass())) {
                if (clazz.getInterfaces().length > 0) {
                    Class<Object> clz = (Class<Object>) clazz.getInterfaces()[0];
                    serviceConfig.setInterface(clz);
                } else {
                    throw new IllegalStateException("Failed to export remote service class " + clazz.getName()
                            + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
                }
            } else {
                serviceConfig.setInterface((Class<Object>) motanService.interfaceClass());
            }

            if (beanFactory != null) {
                serviceConfig.setBeanFactory(beanFactory);
                if (motanService.basicService() != null && motanService.basicService().length() > 0) {
                    serviceConfig.setBasicService(beanFactory.getBean(motanService.basicService(),BasicServiceInterfaceConfig.class));
                }

                if (motanService.export() != null && motanService.export().length() > 0) {
                    serviceConfig.setExport(motanService.export());
                }

                if (motanService.host() != null && motanService.host().length() > 0) {
                    serviceConfig.setHost(motanService.host());
                }

                String protocolValue = null;
                if (motanService.protocol() != null && motanService.protocol().length() > 0) {
                    protocolValue = motanService.protocol();
                } else if (motanService.export() != null && motanService.export().length() > 0) {
                    //TODO
                }
                if (protocolValue != null && protocolValue.length() > 0) {
                    ProtocolConfig protocolConfig = beanFactory.getBean(protocolValue,ProtocolConfig.class);
                    serviceConfig.setProtocol(protocolConfig);
                }

                if (motanService.registry() != null && motanService.registry().length() > 0) {
                    RegistryConfig registryConfig = beanFactory.getBean(motanService.registry(),RegistryConfig.class);
                }
                if (motanService.application() != null && motanService.application().length() > 0) {
                    serviceConfig.setApplication(motanService.application());
                }
                if (motanService.module() != null && motanService.module().length() > 0) {
                    serviceConfig.setModule(motanService.module());
                }
                if (motanService.group() != null && motanService.group().length() > 0) {
                    serviceConfig.setGroup(motanService.group());
                }

                if (motanService.version() != null && motanService.version().length() > 0) {
                    serviceConfig.setVersion(motanService.version());
                }

                try {
                    serviceConfig.afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            serviceConfig.setRef(bean);
            serviceConfigs.add(serviceConfig);
            serviceConfig.export();
        }

        return bean;
    }

    private Object refer(MotanReferer reference, Class<?> referenceClass) {
        String interfaceName;
        if (referenceClass.isInterface()) {
            interfaceName = referenceClass.getName();
        } else {
            throw new IllegalArgumentException("The @Reference undefined interfaceName " +referenceClass.getName());
        }
        String key = reference.group() + "/" + interfaceName + ":" + reference.version();
        RefererConfigBean refererConfigBean = refererConfigBeanMap.get(key);
        if (refererConfigBean != null) {
            refererConfigBean = new RefererConfigBean();

            //refererConfigBean.setBeanFactory(beanFactory);
            if(stringCheckInternal(reference.protocol())) {
                ProtocolConfig protocolConfig = beanFactory.getBean(reference.protocol(),ProtocolConfig.class);
                refererConfigBean.setProtocolConfig(protocolConfig);
            }

            if (stringCheckInternal(reference.basicReferer())) {
                BasicRefererInterfaceConfig basicReferConfig = beanFactory.getBean(reference.basicReferer(),BasicRefererInterfaceConfig.class);
                if(basicReferConfig != null)
                    refererConfigBean.setBasicRefererInterfaceConfig(basicReferConfig);
            }

            if(stringCheckInternal(reference.registry())) {
                RegistryConfig registryConfig = beanFactory.getBean(reference.registry(),RegistryConfig.class);
                refererConfigBean.setRegistryConfig(registryConfig);
            }

            if (stringCheckInternal(reference.group())) {
                refererConfigBean.setGroup(reference.group());
            }

            if (stringCheckInternal(reference.version())) {
                refererConfigBean.setVersion(reference.version());
            }

            if (stringCheckInternal(reference.module())) {
                refererConfigBean.setModule(reference.module());
            }

            try {
                //初始化ReferConfigBean
                refererConfigBean.afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
            refererConfigBeanMap.putIfAbsent(key,refererConfigBean);
        }
        return refererConfigBean;
    }

    private boolean stringCheckInternal(String s) {
        if (s != null && s.length() > 0)
            return true;
        return false;
    }

    private boolean isMatchPackage(Object bean) {
        if(annotationPackages == null || annotationPackages.length == 0)
            return true;
        Class clazz = bean.getClass();
        if (isProxyBean(bean)) {
            clazz = AopUtils.getTargetClass(bean);
        }
        String beanClassName = clazz.getName();
        for (String pkg :annotationPackages) {
            if (beanClassName.startsWith(pkg))
                return true;
        }
        return  false;
    }

    private boolean isProxyBean(Object bean) {
        return AopUtils.isAopProxy(bean);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPackage(String annotationPackage) {
        this.annotationPackage  = annotationPackage;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
