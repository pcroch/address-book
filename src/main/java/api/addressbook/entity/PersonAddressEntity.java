package api.addressbook.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person-address")
public class PersonAddressEntity implements Serializable {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personAddressId;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "person_id")
    private Integer personId;

}
