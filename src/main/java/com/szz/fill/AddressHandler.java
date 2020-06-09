package com.szz.fill;

import com.szz.fill.Address;
import com.szz.fill.datafill.metadata.DataFillMetadata;
import com.szz.fill.datafill.handler.AbstractDataFillHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author szz
 */
public class AddressHandler extends AbstractDataFillHandler {

    public static Map<Object, Address> addressMap = new HashMap();

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
        return this;
    }

}
