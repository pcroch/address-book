package api.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "address")
public class AddressEntity implements Serializable {

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
    private Boolean isPrivate = true;

    @JsonIgnore
//    @ManyToMany(mappedBy = "address")
    @OneToMany(mappedBy = "addressEntity")
    private  Set<PersonAddressEntity> personAddressEntity =  new HashSet<>();

//    @Override
//    public String toString() {
//        return String.format("{addressId: %s, streetNumber: %s, streetName: %s}", addressId, streetNumber, streetName);
//    }
}
