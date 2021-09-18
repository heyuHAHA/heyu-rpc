package com.heyu.rpc.test.springsupport;

import com.heyu.rpc.test.Service;
import springsupport.annotation.MotanReferer;

public class MyController {
    @MotanReferer(basicReferer = "basicRefererConfig")
    private Service service;

    public String  test() {
        return service.testHello("myController");
    }
}
