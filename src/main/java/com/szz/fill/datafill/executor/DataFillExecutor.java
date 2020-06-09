package com.szz.fill.datafill.executor;

import com.szz.fill.datafill.annonation.DataFill;
import com.szz.fill.datafill.handler.DataFillHandler;
import com.szz.fill.datafill.metadata.DataFillMetadata;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author szz
 */
@ToString
public class DataFillExecutor {

    private static ConcurrentHashMap<String, DataFillHandler> handlerKeys = new ConcurrentHashMap();


    public static void executor(Object target, Map args){
        if (null == target) return;
        if (null == args) args = new HashMap();
        Class<?> aClass = target.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            DataFill dataFill = declaredField.getAnnotation(DataFill.class);
            if (null != dataFill) {
                try {
                    String key = dataFill.value();
                    Object value;
                    try {
                        Field field = aClass.getDeclaredField(key);
                        field.setAccessible(true);
                        value = field.get(target);
                        if (value == null) throw new NoSuchFieldException();
                        args.put(key,value);
                    } catch (NoSuchFieldException e) {
                        value = args.get(key);
                    }
                    DataFillMetadata dataFillMetadata = new DataFillMetadata();
                    dataFillMetadata.setFillField(declaredField);
                    dataFillMetadata.setFillObj(target);
                    dataFillMetadata.setSelectionKey(value);
                    dispatcher(dataFill, dataFillMetadata);
                    declaredField.setAccessible(true);
                    Object o = declaredField.get(target);
                    if (null != o && o instanceof Object && !(o instanceof String)){
                        executor(o,args);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    private static void dispatcher(DataFill dataFill, DataFillMetadata dataFillMetadata) {
        Class<? extends DataFillHandler> handler = dataFill.handler();
        DataFillHandler dataFillHandler;
        if ((dataFillHandler = handlerKeys.get(handler.getName())) != null) {
            try {
                dataFillHandler.fill0(dataFillMetadata);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                DataFillHandler fillHandler = handler.newInstance();
                handlerKeys.put(handler.getName(), fillHandler);
                fillHandler.fill0(dataFillMetadata);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
