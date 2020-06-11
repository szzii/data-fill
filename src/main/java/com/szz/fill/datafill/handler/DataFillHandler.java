package com.szz.fill.datafill.handler;

import com.szz.fill.datafill.metadata.DataFillMetadata;
import com.szz.fill.datafill.util.ReflectUtil;

/**
 * 填充Handler的抽象层，定义了一些基本方法,目的为以后更好的多维度扩展
 * @author szz
 */
public interface DataFillHandler {


    /**
     * 最终面向用户的填充方法,可以看出有了一下三件:
     * - 填充字段 fillField
     * - 填充对象 fillObj
     * - 查询条件 selectionKey
     * 即可对对应字段进行逻辑操作
     *
     * @param metadata 元信息
     * @return
     * @throws Exception
     */
    AbstractDataFillHandler fill(DataFillMetadata metadata) throws Exception;



    /**
     * 这里类似模板方法，
     * 但这里实现的并无价值，需要其实现类进行扩展
     * @param metadata 元信息
     * @return
     * @throws Exception
     */
    default AbstractDataFillHandler fill0(DataFillMetadata metadata) throws Exception {
        return fill(metadata);
    }

}

