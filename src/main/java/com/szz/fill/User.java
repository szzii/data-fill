package com.szz.fill;

import com.szz.fill.datafill.annonation.DataFill;
import lombok.Data;
import lombok.ToString;


/**
 * @author szz
 */
@Data
@ToString
public class User {

    private String userId;

    private String name;

    private int age;

    @DataFill(value = "userId",handler = AddressHandler.class)
    private Address address;

    @DataFill(value = "userId",handler = RoleHandler.class)
    private Role role;

}
