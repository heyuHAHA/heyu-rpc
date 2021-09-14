package springsupport;

import cn.hutool.core.collection.ConcurrentHashSet;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.Set;

public class MotanNamespaceHandler extends NamespaceHandlerSupport {
    public static final Set<String> protocolDefineNames = new ConcurrentHashSet<>();
    public static final Set<String> registryDefineNames = new ConcurrentHashSet<>();
    public static final Set<String> basicServiceConfigDefineNames = new ConcurrentHashSet<>();
    @Override
    public void init() {
        registerBeanDefinitionParser("annotation", new MotanAnnotationBeanParser());
    }
}
