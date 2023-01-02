package api.addressbook.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Data
@ToString
public class PersonAddress implements Serializable {

    private Integer personAddressId;
    private Integer personId;
    private Integer addressId;
}
