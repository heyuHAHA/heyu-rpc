package springsupport;

import config.protocol.ProtocolConfig;
import org.springframework.beans.factory.BeanNameAware;

public class ProtocolConfigBean extends ProtocolConfig implements BeanNameAware {

    @Override
    public void setBeanName(String s) {
        this.name = s;
        MotanNamespaceHandler.protocolDefineNames.add(name);
    }


}
