package com.api.addressbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_address")
public class PersonAddressEntity implements java.io.Serializable {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer personAddressId;

    @Column(name = "address_id")
    private String addressId;

    @Column(name = "person_id")
    private String personId;
}
