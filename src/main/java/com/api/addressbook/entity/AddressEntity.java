package com.api.addressbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity implements java.io.Serializable {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer addressId;

    @NotNull
    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "box_number")
    private String boxNumber;

    @NotNull
    @Column(name = "street_name")
    private String streetName;

    @NotNull
    @Column(name = "zipcode")
    private String zipcode;

    @NotNull
    @Column(name = "locality")
    private String locality;

    @NotNull
    @Column(name = "country")
    private String country;

    @Column(name = "is_private")
    private boolean isPrivate = true;

    @ManyToMany
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<PersonEntity> person;
}
