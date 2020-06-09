package domain;

import datafill.DataFill;
import lombok.Data;
import lombok.ToString;

/**
 * @author szz
 */
@Data
@ToString
public class User {

    private String name;

    private int age;

    @DataFill("addrId")
    private Address address;

    private String addrId;
}
