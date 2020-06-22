package com.szz.fill.test;


import com.szz.fill.test.model.User;
import sun.misc.UCDecoder;

/**
 * @author szz
 */
public class TestMain {

    public User findUser(String userId){
        User user = new User();
        user.setUserId(userId);
        user.setAge(18);
        user.setName("单昭铮");
        return user;
    }


    public static void main(String[] args) {
        TestMain testMain = new TestMain();

        ProxyFactory<TestMain> proxyFactory = new ProxyFactory(testMain);
        TestMain testMainProxy = proxyFactory.newInstance(testMain);

        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                User user = testMainProxy.findUser(2+"");
                System.out.println(user.toString());
            }).start();
        }
        /*User user = testMainProxy.findUser("1");
        System.out.println(user.toString());*/
    }
}
