package com.api.addressbook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity implements java.io.Serializable {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer personId;
    @NotNull
    @Column(name = "firstname")
    String firstname;

    @Column(name = "secondname")
    String secondname;

    @Column(name = "lastname")
    String lastname;

    //todo adding the foregin key Addres Entity
    @Override
    public String toString() {
        return String.format("{personId: %s, firstname: %s, secondname: %s, lastname: %s}", personId, firstname, secondname, lastname);
    }

}
