package com.szz.fill;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author szz
 */
public class TestMain {

    public User findUser(){
        User user = new User();
        user.setUserId("2");
        user.setAge(18);
        user.setName("单昭铮");
        return user;
    }


    public static void main(String[] args) throws NoSuchFieldException {
        TestMain testMain = new TestMain();
        //System.out.println(testMain.getClass() instanceof Integer);
        ProxyFactory<TestMain> proxyFactory = new ProxyFactory(testMain);
        TestMain testMainProxy = proxyFactory.newInstance(testMain);
        User user = testMainProxy.findUser();
        System.out.println(user.toString());
    }
}
