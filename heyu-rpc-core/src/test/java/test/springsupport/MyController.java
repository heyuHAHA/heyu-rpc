package test.springsupport;

import test.Service;
import springsupport.annotation.MotanReferer;

public class MyController {
    @MotanReferer(basicReferer = "basicRefererConfig")
    private Service service;

    public String  test() {
        return service.testHello("myController");
    }
}
