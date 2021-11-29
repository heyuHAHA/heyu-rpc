package util;

import cn.hutool.core.util.StrUtil;
import common.RpcConstants;
import rpc.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlUtils {

    public static List<URL> parseURLs(String address, Map<String,String> parameters) {
        if (address == null || address.length() == 0){
            return null;
        }
        //如果address有多个ip:port,就把它们split出来
        String[] addresses = RpcConstants.REGISTY_SPLIT_PATTERN.split(address);
        List<URL> registries = new ArrayList<>();

        for (String addr : addresses) {
            String defaultProtocol = parameters == null ? null : parameters.get("");
            if (defaultProtocol == null || defaultProtocol.length() == 0) {
                defaultProtocol = "motan";
            }


            int defaultPort = Integer.parseInt(parameters == null ? "0" : parameters.get("port") == null ? "0" : parameters.get("port"));
            String defaultPath = parameters == null ? null : parameters.get("path");
            Map<String,String> defaultParameters = parameters == null ? null : new HashMap<>(parameters);

            //上面已经拿到了对应defaultParameters中的以下参数
            //而且registry url 的参数是在"？"后面的一对对key-value,不应该包含以下参数，以下参数属于"?"前面的内容
            if(defaultParameters != null) {
                defaultParameters.remove("");
                defaultParameters.remove("port");
                defaultParameters.remove("path");
                defaultParameters.remove("host");
            }

            URL u = URL.valueOf(addr);
            u.addParameters(parameters);
            boolean changed = false;

            String protocol = u.getProtocol();
            String path = u.getPath();
            String host = u.getHost();
            int port = u.getPort();
            Map<String,String> parameters_ = new HashMap<>(u.getParameters());

            if (StrUtil.isBlank(protocol)) {
                changed = true;
                protocol = defaultProtocol;
            }

            if (port <= 0) {
                if (defaultPort > 0) {
                    changed = true;
                    port = defaultPort;
                } else {
                    changed = true;
                    port = 0;
                }
            }

            if(StrUtil.isBlank(path)) {
                if (defaultPath != null && defaultPath.length() > 0) {
                    changed = true;
                    path = defaultPath;
                }
            }

            if(defaultParameters != null && defaultParameters.size() > 0) {
                for (Map.Entry<String,String> entry : defaultParameters.entrySet()) {
                    String key = entry.getKey();
                    String defaultValue = entry.getValue();
                    if (defaultValue != null && defaultValue.length() > 0) {
                        String value = parameters_.get(key);
                        if (value == null || value.length() == 0) {
                            changed = true;
                            parameters_.put(key,defaultValue);
                        }
                    }

                }
            }

            if(changed) {
                u = new URL(protocol,host, port, path, parameters_);
            }

            registries.add(u);


        }



        return registries;
    }
}
