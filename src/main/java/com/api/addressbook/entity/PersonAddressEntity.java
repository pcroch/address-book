package com.api.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_address")
public class PersonAddressEntity   {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personAddressId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_id")
    private PersonEntity person;
}
