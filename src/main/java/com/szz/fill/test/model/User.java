package com.szz.fill.test.model;

import com.szz.fill.datafill.annonation.DataFill;
import com.szz.fill.datafill.annonation.DataFillEnable;
import com.szz.fill.test.myhandler.AddressHandler;
import com.szz.fill.test.myhandler.RoleHandler;
import lombok.Data;
import lombok.ToString;


/**
 * @author szz
 */
@Data
@ToString
@DataFillEnable
public class User {

    private String userId;

    private String name;

    private int age;

    @DataFill(value = "userId",handler = AddressHandler.class)
    private Address address;

    @DataFill(value = "userId",handler = RoleHandler.class)
    private Role role;

}
