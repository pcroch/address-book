package api.addressbook.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_address")
public class PersonAddressEntity implements Serializable {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer personAddressId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "person_address_per_code",
            joinColumns =
                    { @JoinColumn(name = "person_address_id", referencedColumnName = "person_address_id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "qr_code_id", referencedColumnName = "qr_code_id") })
    private QRCodeEntity qrcodeEntity;
}
