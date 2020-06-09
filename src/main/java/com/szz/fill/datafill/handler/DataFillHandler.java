package com.szz.fill.datafill.handler;

import com.szz.fill.datafill.metadata.DataFillMetadata;

/**
 * @author szz
 */
public interface DataFillHandler {


    AbstractDataFillHandler fill(DataFillMetadata metadata) throws Exception;


    default AbstractDataFillHandler fill0(DataFillMetadata metadata) throws Exception {
        return fill(metadata);
    }

}

