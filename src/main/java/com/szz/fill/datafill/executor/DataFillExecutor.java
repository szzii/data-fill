package com.szz.fill.datafill.executor;

import com.szz.fill.datafill.annonation.DataFill;
import com.szz.fill.datafill.annonation.DataFillEnable;
import com.szz.fill.datafill.handler.DataFillHandler;
import com.szz.fill.datafill.metadata.DataFillMetadata;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author szz
 */
@ToString
public class DataFillExecutor {

    private static ConcurrentHashMap<String, DataFillHandler> handlerKeys = new ConcurrentHashMap();

    private static ThreadLocal tl = ThreadLocal.withInitial(new Supplier<Consumer>() {
        @Override
        public Consumer get() {
            return null;
        }
    });

    public static void execute(Object target, Map args) {
        if (null == target) return;
        if (null == args) args = new HashMap();
        Class<?> aClass = target.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            DataFill dataFill = declaredField.getAnnotation(DataFill.class);
            if (null != dataFill) {
                try {
                    DataFillMetadata metadata = new DataFillMetadata();
                    metadata.setFillField(declaredField);
                    metadata.setFillObj(target);
                    metadata.setSelectionKey(findParam(dataFill, target, args));
                    dispatcher(dataFill, metadata);
                    declaredField.setAccessible(true);
                    Object sinkObj = declaredField.get(target);
                    if (null != sinkObj && null != sinkObj.getClass().getAnnotation(DataFillEnable.class)) {
                        execute(sinkObj, args);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static Object findParam(DataFill dataFill, Object target, Map args) {
        String key = dataFill.value();
        Object value = null;
        try {
            Field field = target.getClass().getDeclaredField(key);
            field.setAccessible(true);
            value = field.get(target);
            if (value == null) throw new NoSuchFieldException();
            args.put(key, value);
        } catch (NoSuchFieldException e) {
            value = args.get(key);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
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
