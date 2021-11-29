package util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectUtil {
    public static final String PARAM_CLASS_SPLIT = ",";
    private static final Map<Class<?>,String> class2Name = new ConcurrentHashMap<>();


    public static String getMethodParamDesc(Method method) {
        StringBuilder builder = new StringBuilder();
        Class<?>[] clzs = method.getParameterTypes();

        for (Class<?> clz : clzs) {
            String className = getName(clz);
            builder.append(className).append(PARAM_CLASS_SPLIT);
        }
        return builder.substring(0,builder.length() - 1);
    }

    public static String getName(Class<?> clz) {
        if (clz == null)
            return null;
        String className = class2Name.get(clz);
        if (className != null) {
            return className;
        }
        className = getNameWithoutCache(clz);
        class2Name.putIfAbsent(clz,className);
        return className;
    }

    private static String getNameWithoutCache(Class<?> clz) {
        if (!clz.isArray()) {
            return clz.getName();
        }
        StringBuilder sb = new StringBuilder();
        //需要支持一维数组，二维数组
        while (clz.isArray()) {
            sb.append("[]");
            clz = clz.getComponentType();
        }
        return clz.getName() + sb.toString();
    }
}
