package com.szz.fill;



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


    public static void main(String[] args) throws NoSuchFieldException {
        TestMain testMain = new TestMain();
        ProxyFactory<TestMain> proxyFactory = new ProxyFactory(testMain);
        TestMain testMainProxy = proxyFactory.newInstance(testMain);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                User user = testMainProxy.findUser(1+"");
                System.out.println(user.toString());
            }).start();
        }
        /*User user = testMainProxy.findUser("1");
        System.out.println(user.toString());*/
    }
}
