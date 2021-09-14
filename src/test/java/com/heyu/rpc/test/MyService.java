package com.heyu.rpc.test;

import org.springframework.stereotype.Component;
import springsupport.annotation.MotanService;

@Component
@MotanService
public class MyService implements Service {
    @Override
    public void testHello(String str) {
        System.out.println("hello " +str);
    }

    @Override
    public Object get(Object o) {
        return null;
    }
}
