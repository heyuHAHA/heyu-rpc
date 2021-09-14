package com.heyu.rpc.test.springsupport;


import com.heyu.rpc.test.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLOutput;

public class MotanReferTest {


    private Service service;

    @Before
    public void loadSpring() {
        ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext("classpath:application-text.xml");
        Service service = application.getBean("referService",Service.class);
        Assert.assertNotNull(service);
    }


    @Test
    public void test() {
        System.out.println("hello");
    }
}
