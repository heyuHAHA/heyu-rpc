package springsupport.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface MotanReferer {


    Class<?> interfaceClass() default  void.class;
    //分组
    String group() default "";
    //服务版本
    String version() default "";
    //注册中心的配置列表
    String registry() default "";
    //模块名称
    String module() default "";
    //应用名称
    String application() default "";

    String protocol() default "";

    String basicReferer() default "";
}
