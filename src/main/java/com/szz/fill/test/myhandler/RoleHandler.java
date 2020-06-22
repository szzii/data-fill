package com.szz.fill.test.myhandler;

import com.szz.fill.datafill.handler.AbstractDataFillHandler;
import com.szz.fill.datafill.handler.DataFillHandler;
import com.szz.fill.datafill.metadata.DataFillMetadata;
import com.szz.fill.test.model.Role;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author szz
 */
public class RoleHandler extends AbstractDataFillHandler {

    public static Map<Object, Role> roleMap = new ConcurrentHashMap<>();

    static {
        roleMap.put("1",new Role("普通用户"));
        roleMap.put("2",new Role("管理员"));
        roleMap.put("3",new Role("VIP"));
        roleMap.put("4",new Role("开发者"));
    }

    @Override
    public DataFillHandler fill(DataFillMetadata metadata) throws Exception {
        Role role = roleMap.get(metadata.getSelectionKey());
        if (null != role){
            Field fillField = metadata.getFillField();
            fillField.setAccessible(true);
            fillField.set(metadata.getFillObj(),role);
        }
        System.out.println(Thread.currentThread().getName()+": 开始填充角色");
        return this;
    }
}
