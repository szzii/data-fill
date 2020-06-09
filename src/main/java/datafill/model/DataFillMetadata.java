package datafill.model;

import java.lang.reflect.Field;

/**
 * @author szz
 */
public class DataFillMetadata {

    private Field fillField;

    private Object fillObj;

    private Object key;

    public Field getFillField() {
        return fillField;
    }

    public void setFillField(Field fillField) {
        this.fillField = fillField;
    }

    public Object getFillObj() {
        return fillObj;
    }

    public void setFillObj(Object fillObj) {
        this.fillObj = fillObj;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }
}
