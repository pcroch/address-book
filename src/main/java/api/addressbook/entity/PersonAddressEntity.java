package api.addressbook.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_address")
public class PersonAddressEntity implements Serializable {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Integer personAddressId;

//    @Column(name = "address_id")
//    private Integer addressId;
//
//    @Column(name = "person_id")
//    private Integer personId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;

//    @Column(name = "address_fk")
//    private Integer addressFk;
//
//    @Column(name = "person_fk")
//    private Integer personFk;
}
