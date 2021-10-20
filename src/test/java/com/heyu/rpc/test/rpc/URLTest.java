package com.heyu.rpc.test.rpc;

import org.junit.Assert;
import org.junit.Test;
import rpc.URL;

public class URLTest {

    @Test
    public void test1ValueOf() {
        URL url = URL.valueOf("127.0.0.1:8082");

        Assert.assertNotNull(url);
        Assert.assertEquals("127.0.0.1",url.getHost());
        Assert.assertEquals(8082,url.getPort());
        Assert.assertEquals(null,url.getProtocol());
        Assert.assertEquals(null,url.getPath());
        Assert.assertEquals(0,url.getParameters().size());

    }

    @Test
    public void test2ValurOf(){
        URL url = URL.valueOf("direct://127.0.0.1:8002/com.weibo.api.motan.registry.RegistryService?group=default_rpc");
        Assert.assertNotNull(url);
        Assert.assertEquals("127.0.0.1",url.getHost());
        Assert.assertEquals(8002,url.getPort());
        Assert.assertEquals("direct",url.getProtocol());
        Assert.assertEquals("com.weibo.api.motan.registry.RegistryService",url.getPath());
        Assert.assertEquals(1,url.getParameters().size());
    }
}
