package com.heyu.rpc.test;

import org.springframework.stereotype.Component;
import springsupport.annotation.MotanService;



public class MyService implements Service {
    @Override
    public String testHello(String str) {
        return "hello " +str;
    }

    @Override
    public Object get(Object o) {
        return null;
    }
}
