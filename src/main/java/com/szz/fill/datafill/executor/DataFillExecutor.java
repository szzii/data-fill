package com.szz.fill.datafill.executor;

import com.szz.fill.datafill.annonation.DataFill;
import com.szz.fill.datafill.annonation.DataFillEnable;
import com.szz.fill.datafill.handler.DataFillHandler;
import com.szz.fill.datafill.metadata.DataFillMetadata;
import javafx.concurrent.Task;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author szz
 */
public class DataFillExecutor {

    private static ConcurrentHashMap<String, DataFillHandler> handlerKeys = new ConcurrentHashMap();


    private static final List<Callable<Object>> fillGroup = new ArrayList<>();

    public static ThreadPoolExecutor pool;

    static {
        pool = new ThreadPoolExecutor(
                2,
                2,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }


    public static void execute(Object target,Map args) {
        execute0(target,args);
        executeAfter();
    }



    private static void execute0(Object target, final Map args) {
        if (null == target) return;
        Class<?> aClass = target.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            DataFill dataFill = declaredField.getAnnotation(DataFill.class);
            if (null != dataFill) {

                DataFillMetadata metadata = new DataFillMetadata();
                metadata.setFillField(declaredField);
                metadata.setFillObj(target);
                metadata.setSelectionKey(findParam(dataFill, target, args));

                fillGroup.add(() -> {
                    dispatcher(dataFill, metadata);
                    declaredField.setAccessible(true);
                    Object sinkObj = null;
                    try {
                        sinkObj = declaredField.get(target);
                        if (null != sinkObj && null != sinkObj.getClass().getAnnotation(DataFillEnable.class)) {
                            sinkExecute(sinkObj, args);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
            }
        }
    }



    private static void sinkExecute(Object target, final Map args) {
        if (null == target) return;
        Class<?> aClass = target.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            DataFill dataFill = declaredField.getAnnotation(DataFill.class);
            if (null != dataFill) {

                DataFillMetadata metadata = new DataFillMetadata();
                metadata.setFillField(declaredField);
                metadata.setFillObj(target);
                metadata.setSelectionKey(findParam(dataFill, target, args));

                dispatcher(dataFill, metadata);
                declaredField.setAccessible(true);
                Object sinkObj = null;
                try {
                    sinkObj = declaredField.get(target);
                    if (null != sinkObj && null != sinkObj.getClass().getAnnotation(DataFillEnable.class)) {
                        sinkExecute(sinkObj, args);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void executeAfter(){
        try {
            List<Future<Object>> futures = pool.invokeAll(fillGroup);
            for (Future<Object> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
