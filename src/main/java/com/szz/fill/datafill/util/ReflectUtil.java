package com.szz.fill.datafill.util;

import java.lang.reflect.Field;

/**
 * @author szz
 */
public class ReflectUtil {


    public static void replaceFillObject(Object source,Object target) throws Exception {
        if (null == source) return;
        if (null == target){
            target = target.getClass().newInstance();
        }
        Field[] declaredFields = source.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            try {
                Field field = target.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(target,declaredField.get(source));
            } catch (NoSuchFieldException e) {}
        }
    }
}
