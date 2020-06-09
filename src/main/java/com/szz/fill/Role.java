package com.szz.fill;

import com.szz.fill.datafill.annonation.DataFill;
import lombok.Data;
import lombok.ToString;

/**
 * @author szz
 */
@ToString
@Data
public class Role {

    private String roleName;

    @DataFill(value = "userId",handler = AuthorityHandler.class)
    private Authority authority;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}



