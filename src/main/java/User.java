import datafill.annonation.DataFill;
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

    @DataFill(value = "addrId",handler = AddressHandler.class)
    private Address address;

    private String addrId;
}
