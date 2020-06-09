import datafill.model.DataFillMetadata;
import datafill.handler.AbstractDataFillHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author szz
 */
public class AddressHandler extends AbstractDataFillHandler {

    public static Map<Object,Address> addressMap = new HashMap();

    static {
        addressMap.put("1",new Address("159","济南"));
        addressMap.put("2",new Address("123","北京"));
        addressMap.put("3",new Address("138","吉林"));
        addressMap.put("4",new Address("199","上海"));
    }


    @Override
    public AbstractDataFillHandler fill(DataFillMetadata metadata){
        this.validation(metadata);

        Address address = addressMap.get(metadata.getKey());
        if (null != address){
            try {
                Field fillField = metadata.getFillField();
                fillField.setAccessible(true);
                fillField.set(metadata.getFillObj(),address);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }





}
