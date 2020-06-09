package aop;

import datafill.DataFill;
import domain.Address;
import domain.IDataBase;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author szz
 */
public class ProxyFactory<T> implements MethodInterceptor {

    private T target;

    public ProxyFactory(T target) {
        this.target = target;
    }

    public T newInstance(T target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T)enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object invoke = method.invoke(target, objects);
        Field[] declaredFields = invoke.getClass().getDeclaredFields();
        DataFill annotation = null;
        Field fillField = null;
        for (Field declaredField : declaredFields) {
            annotation = declaredField.getAnnotation(DataFill.class);
            if (null != annotation){
                declaredField.setAccessible(true);
                fillField = declaredField;
                break;
            }
        }
        if (null == annotation) return invoke;
        String value = annotation.value();
        Field field = invoke.getClass().getDeclaredField(value);
        field.setAccessible(true);
        Object o1 = field.get(invoke);
        AddressHandler(fillField,invoke,o1);
        return invoke;
    }

    public void AddressHandler(Field fillField,Object fillObj,Object key){
        Map<Object, Address> map = IDataBase.addressMap;
        Address address = map.get(key);
        if (null != address){
            try {
                fillField.set(fillObj,address);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
