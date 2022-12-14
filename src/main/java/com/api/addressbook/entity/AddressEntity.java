package com.api.addressbook.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address") // , schema = "addressBook"
public class AddressEntity {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer addressId;

}
