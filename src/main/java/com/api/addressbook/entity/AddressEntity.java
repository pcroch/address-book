package com.api.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity    {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;

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

//    @JsonIgnore
    @OneToMany( mappedBy = "address")
    private List<PersonAddressEntity> personAddress;
}
