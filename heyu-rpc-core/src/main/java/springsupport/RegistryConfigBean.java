package springsupport;

import config.registry.RegistryConfig;
import org.springframework.beans.factory.BeanNameAware;

public class RegistryConfigBean extends RegistryConfig implements BeanNameAware {
    @Override
    public void setBeanName(String s) {
        setId(s);
        setName(s);
        MotanNamespaceHandler.registryDefineNames.add(s);
    }
}
