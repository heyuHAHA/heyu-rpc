package common;

import java.io.Serializable;

public class URL implements Serializable {

    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
