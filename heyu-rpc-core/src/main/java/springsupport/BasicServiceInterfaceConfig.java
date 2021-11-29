package springsupport;

import config.protocol.ProtocolConfig;
import config.registry.RegistryConfig;

public class BasicServiceInterfaceConfig {
    private boolean isDefault;

    private String export;


    private RegistryConfig registryConfig;

    private ProtocolConfig protocolConfig;

    private String id;

    protected String protocolName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isDefault() {
        return isDefault;
    }


    public void setProtocol(ProtocolConfig protocol) {
        this.protocolConfig = protocol;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }
}
