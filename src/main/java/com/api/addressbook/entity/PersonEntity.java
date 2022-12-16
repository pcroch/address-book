package com.api.addressbook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity implements java.io.Serializable {

    @Id
    @Column(name = "person_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personId;
    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @Column(name = "secondname")
    private String secondname;

    @Column(name = "lastname")
    private String lastname;

    @ManyToMany(mappedBy = "person")
    private Set<AddressEntity> address;

    @Override
    public String toString() {
        return String.format("{personId: %s, firstname: %s, secondname: %s, lastname: %s}", personId, firstname, secondname, lastname);
    }
}
