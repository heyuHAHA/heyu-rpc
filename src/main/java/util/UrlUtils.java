package util;

import cn.hutool.core.util.StrUtil;
import common.RpcConstants;
import rpc.URL;

import java.util.ArrayList;
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
            String protocol = parameters == null ? null : parameters.get("protocol");
            if (protocol == null || protocol.length() == 0) {
                protocol = "motan";
            }

            int port = Integer.valueOf(parameters == null ? null : parameters.get("port"));
            String path = parameters == null ? null : parameters.get("path");

            URL u = URL.valueOf(addr);
            u.addParameters(parameters);


        }



        return registries;
    }
}
