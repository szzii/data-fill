package datafill.executor;

import datafill.annonation.DataFill;
import datafill.handler.DataFillHandler;
import datafill.model.DataFillMetadata;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author szz
 */
public class DataFillExecutor {

    private static ConcurrentHashMap<String,DataFillHandler> handlerKeys = new ConcurrentHashMap();

    public static void executor(Object target){
        if (null == target) return;
        Class<?> aClass = target.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            DataFill dataFill = declaredField.getAnnotation(DataFill.class);
            if (null != dataFill){
                DataFillMetadata dataFillMetadata = new DataFillMetadata();
                dataFillMetadata.setFillField(declaredField);
                dataFillMetadata.setFillObj(target);
                String value = dataFill.value();
                try {
                    Field field = aClass.getDeclaredField(value);
                    field.setAccessible(true);
                    Object key = field.get(target);
                    dataFillMetadata.setKey(key);
                    dispatcher(dataFill,dataFillMetadata);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    continue;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    private static void dispatcher(DataFill dataFill, DataFillMetadata dataFillMetadata){
        Class<? extends DataFillHandler> handler = dataFill.handler();
        DataFillHandler dataFillHandler;
        if ((dataFillHandler = handlerKeys.get(handler.getName()) ) != null){
            dataFillHandler.fill(dataFillMetadata);
        }else {
            try {
                DataFillHandler fillHandler = handler.newInstance();
                handlerKeys.put(handler.getName(),fillHandler);
                fillHandler.fill(dataFillMetadata);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


}
