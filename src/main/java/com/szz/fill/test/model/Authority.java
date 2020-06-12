package com.szz.fill.test.model;

import com.szz.fill.datafill.annonation.DataFill;
import com.szz.fill.datafill.annonation.DataFillEnable;
import com.szz.fill.test.myhandler.AddressHandler;
import lombok.Data;

/**
 * @author szz
 */
@Data
@DataFillEnable
public class Authority {

    private Integer level;

    public Authority(Integer level) {
        this.level = level;
    }

    @DataFill(value = "userId",handler = AddressHandler.class)
    private Address address;
}
