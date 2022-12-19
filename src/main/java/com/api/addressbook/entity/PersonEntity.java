package com.api.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity   {

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

//    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private List<PersonAddressEntity> personAddress;

    @Override
    public String toString() {
        return String.format("{personId: %s, firstname: %s, secondname: %s, lastname: %s}", personId, firstname, secondname, lastname);
    }
}
