package springsupport.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface MotanService {

    Class<?> interfaceClass() default void.class;

    String basicService() default "";

    String export() default "";

    String host() default "";

    String protocol() default "";

    // 注册中心的配置列表
    String registry() default "";
    // 应用名称
    String application() default "";

    // 模块名称
    String module() default "";

    // 分组
    String group() default "";

    // 服务版本
    String version() default "";

    // 代理类型
    String proxy() default "";

    // 过滤器
    String filter() default "";
}
