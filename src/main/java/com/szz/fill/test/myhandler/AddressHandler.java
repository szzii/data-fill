package com.szz.fill.test.myhandler;

import com.szz.fill.datafill.metadata.DataFillMetadata;
import com.szz.fill.datafill.handler.AbstractDataFillHandler;
import com.szz.fill.test.model.Address;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author szz
 */
public class AddressHandler extends AbstractDataFillHandler {

    public static Map<Object, Address> addressMap = new ConcurrentHashMap();

    static {
        addressMap.put("1",new Address("159","济南"));
        addressMap.put("2",new Address("123","北京"));
        addressMap.put("3",new Address("138","吉林"));
        addressMap.put("4",new Address("199","上海"));
    }



    @Override
    public AbstractDataFillHandler fill(DataFillMetadata metadata) throws Exception {
        Address address = addressMap.get(metadata.getSelectionKey());
        if (null != address){
            Field fillField = metadata.getFillField();
            fillField.setAccessible(true);
            fillField.set(metadata.getFillObj(),address);
        }
        System.out.println(Thread.currentThread().getName()+": 开始填充地址");
        return this;
    }


    @Override
    protected void exceptionCaught(Throwable cause) throws Exception {
        super.exceptionCaught(cause);
    }
}
