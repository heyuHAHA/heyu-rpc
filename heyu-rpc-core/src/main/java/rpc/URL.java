package rpc;

import cn.hutool.core.util.StrUtil;
import exception.RpcServiceException;

import java.util.HashMap;
import java.util.Map;

public class URL {
    private String protocol;
    private String host;
    private int port;
    private String path;
    private Map<String,String> parameters;


    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public static URL valueOf(String url) {
        if (StrUtil.isBlank(url)) {
            throw new RpcServiceException("url is null" );
        }

        String protocol = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String,String> parameters = new HashMap<>();
        int i = url.indexOf("?");
        if ( i >= 0) {
            // xxx=aaa&yyy=bbb
            String[] params = url.substring(i + 1).split("\\&");
            for (String param : params) {
                //param : xxx=aaa
                param = param.trim(); //去掉多余空格
                if (param.length() > 0) {
                    int j = param.indexOf("=");
                    if (j >= 0) {
                       parameters.put(param.substring(0,j),param.substring(j+1));
                    } else {
                        parameters.put(param,param);
                    }

                }
            }
            //丢掉i后的字符串
            url = url.substring(0,i);
        }

        i = url.indexOf("://");
        if (i >= 0) {
            if (i == 0) throw new IllegalArgumentException("url missing protocol : " + url);
            protocol = url.substring(0,i);
            url = url.substring(i+3);
        }

        i = url.indexOf("/");
        if ( i >= 0) {
            path = url.substring(i+1);
            url = url.substring(0,i);
        }

        i = url.indexOf(":");
        if (i >= 0){
            port = Integer.parseInt(url.substring(i+1));
            url = url.substring(0,i);
        }
        if (url.length() > 0) host = url;
        return new URL(protocol,host,port,path,parameters);
    }

    public void addParameters(Map<String,String> paramsMap) {
        parameters.putAll(paramsMap);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public URL createCopy() {
        Map<String,String> params = new HashMap<>();
        if (this.parameters != null) {
            params.putAll(this.parameters);
        }
        return new URL(protocol,host,port,path,params);
    }

    public void addParameters(String nodeType, String service) {
        if (StrUtil.isEmpty(nodeType) || StrUtil.isEmpty(service))
            return;
        this.parameters.put(nodeType,service);
    }

    public String getUri() {
        return protocol + "://" + host + ":" + port +
                "/" + path;
    }

    public String getServerPortStr() {
        return buildHostPorStr(host,port);
    }

    private String buildHostPorStr(String host, int defaultPort) {
        if (defaultPort < 0)
            return host;
        int idx = host.indexOf(":");
        if (idx < 0) {
            return host + ":"+defaultPort;
        }
        int port = Integer.parseInt(host.substring(idx+1));
        if (port <= 0) {
            return host.substring(0,idx+1) + defaultPort;
        }
        return host;
    }
}
