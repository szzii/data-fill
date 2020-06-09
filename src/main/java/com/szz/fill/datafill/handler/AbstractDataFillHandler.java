package com.szz.fill.datafill.handler;

import com.szz.fill.datafill.metadata.DataFillMetadata;

/**
 * @author szz
 */
public abstract class AbstractDataFillHandler implements DataFillHandler {


    protected void validation(DataFillMetadata metadata) throws Exception{
        if (metadata == null){
            throw new IllegalArgumentException();
        }else if (metadata.getFillField() == null){
            throw new IllegalArgumentException();
        }else if (metadata.getSelectionKey() == null){
            throw new IllegalArgumentException();
        }
    }


    public AbstractDataFillHandler fill0(DataFillMetadata metadata) throws Exception {
        try {
            validation(metadata);
            fill(metadata);
        }catch (Exception exception){
            exceptionCaught(exception);
        }
        return this;
    }


    protected void exceptionCaught(Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
