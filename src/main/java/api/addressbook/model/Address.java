package api.addressbook.model;


import api.addressbook.entity.PersonAddressEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Builder
@Data
@ToString
public class Address implements Serializable {

    private Integer addressId;
    private String streetNumber;
    private String boxNumber;
    private String streetName;
    private String zipcode;
    private String locality;
    private String country;
    private Boolean isPrivate;
    private Set<PersonAddressEntity> personAddressEntity;
}