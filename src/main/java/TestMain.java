import aop.ProxyFactory;
import domain.IDataBase;
import domain.User;

/**
 * @author szz
 */
public class TestMain {

    public User findUser(){
        User user = new User();
        user.setAge(18);
        user.setName("单昭铮");
        user.setAddrId("1");
        return user;
    }


    public static void main(String[] args) {
        TestMain testMain = new TestMain();
        ProxyFactory<TestMain> proxyFactory = new ProxyFactory(testMain);
        TestMain testMainProxy = proxyFactory.newInstance(testMain);
        User user = testMainProxy.findUser();
        System.out.println(user.toString());
    }
}
