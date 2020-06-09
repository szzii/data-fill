package com.szz.fill;

import com.szz.fill.Role;
import com.szz.fill.datafill.handler.AbstractDataFillHandler;
import com.szz.fill.datafill.metadata.DataFillMetadata;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author szz
 */
public class RoleHandler extends AbstractDataFillHandler {

    public static Map<Object, Role> roleMap = new HashMap();

    static {
        roleMap.put("1",new Role("普通用户"));
        roleMap.put("2",new Role("管理员"));
        roleMap.put("3",new Role("VIP"));
        roleMap.put("4",new Role("开发者"));
    }

    @Override
    public AbstractDataFillHandler fill(DataFillMetadata metadata) throws Exception {
        Role role = roleMap.get(metadata.getSelectionKey());
        Field fillField = metadata.getFillField();
        fillField.setAccessible(true);
        fillField.set(metadata.getFillObj(),role);
        return null;
    }
}
