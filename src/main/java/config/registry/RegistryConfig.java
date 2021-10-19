package config.registry;

import cn.hutool.core.util.StrUtil;
import exception.HeyuRpcFrameworkException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;

public class RegistryConfig {

    private String id;

    private Boolean isDefault;

    // 注册协议
    private String regProtocol;

    // 注册中心地址，支持多个ip+port，格式：ip1:port1,ip2:port2,ip3，如果没有port，则使用默认的port
    private String address;

    // 注册中心缺省端口
    private Integer port;

    public void appendConfigParams(Map<String,String> parameters) {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            try {
                String name = method.getName();
                if (isGetConfigMethod(method)) {
                    int idx = name.startsWith("get") ? 3 : 2;
                    String key = name.substring(idx, idx + 1).toLowerCase(Locale.ROOT) + name.substring(idx + 1);
                    Object value = method.invoke(this);
                    if (value == null || StrUtil.isBlank(String.valueOf(value))) {
                        continue;
                    }
                    parameters.put(key,String.valueOf(value));
                }
            } catch (Exception e) {
                throw new HeyuRpcFrameworkException("Error occurs on when append parameters to Config : " +  this.getClass().getSimpleName() + " , " +  method.getName());
            }
        }
    }

    private boolean isGetConfigMethod(Method method) {
        boolean checkMethod = (method.getName().startsWith("get") || method.getName().startsWith("is")) && !"isDefault".equals(method.getName())
                && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0
                && isPrimitive(method.getReturnType());

        return checkMethod;

    }

    private boolean isPrimitive(Class<?> returnType) {
        return returnType.isPrimitive() || returnType == String.class || returnType == Character.class || returnType == Integer.class || returnType == Short.class
                || returnType == Long.class || returnType == Float.class || returnType == Double.class || returnType == Byte.class || returnType == Boolean.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // 注册配置名称
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean isDefault() {
        return isDefault;
    }



    public String getRegProtocol() {
        return regProtocol;
    }

    public void setRegProtocol(String regProtocol) {
        this.regProtocol = regProtocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
