package com.szz.fill.datafill.metadata;

import java.lang.reflect.Field;

/**
 * @author szz
 */
public class DataFillMetadata {

    private Field fillField;

    private Object fillObj;

    private Object SelectionKey;

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

    public Object getSelectionKey() {
        return SelectionKey;
    }

    public void setSelectionKey(Object selectionKey) {
        SelectionKey = selectionKey;
    }
}
