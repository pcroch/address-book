package api.addressbook.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.Set;

@Builder
@Data
@ToString
public class Person implements Serializable {

    private Integer personId;
    private String firstname;
    private String secondname;
    private String lastname;
    private Set<Address> address;

}