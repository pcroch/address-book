package api.addressbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity implements Serializable {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "person_address",
            joinColumns = {@JoinColumn(name = "person_fk",
                    referencedColumnName = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "address_fk",
                    referencedColumnName = "address_id")})
    private Set<AddressEntity> address = new HashSet<>();

    @Override
    public String toString() {
        return String.format("{personId: %s, firstname: %s, secondname: %s, lastname: %s, Address' Set: %s}", personId, firstname, secondname, lastname, address);
    }
}
