package common;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class URL implements Serializable {

    private String protocol;
    private String host;
    private Integer port;
    private String bindIp;
    private Integer bindPort;
    private int accepts = 0;

    public int getAccepts() {
        return accepts;
    }

    public void setAccepts(int accepts) {
        this.accepts = accepts;
    }

    public String getBindIp() {
        return bindIp;
    }

    public void setBindIp(String bindIp) {
        this.bindIp = bindIp;
    }

    public Integer getBindPort() {
        return bindPort;
    }

    public void setBindPort(Integer bindPort) {
        this.bindPort = bindPort;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }



    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public InetSocketAddress toInetSocketAddress() {
        return new InetSocketAddress(getHost(),getPort());
    }

    public String getHost() {
        return  host;
    }

    public Integer getPort() {
        return port;
    }

}
