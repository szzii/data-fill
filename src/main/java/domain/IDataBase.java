package domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author szz
 */
@Data
public class IDataBase {

    public static Map<Object,Address> addressMap = new HashMap(1 << 2);

    static {
        addressMap.put("1",new Address("159","济南"));
        addressMap.put("2",new Address("123","北京"));
        addressMap.put("3",new Address("138","吉林"));
        addressMap.put("4",new Address("199","上海"));
    }
}
