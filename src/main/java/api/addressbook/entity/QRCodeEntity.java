package api.addressbook.entity;

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
@Table(name = "qr_code")
public class QRCodeEntity implements Serializable {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personAddressId;

    @Column(name = "qr_code_name")
    private String qrCodeName;

    @Column(name = "qr_code_image")
    private byte[] qrCodeImage;


    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_address_id", referencedColumnName = "person_address_id")
    private PersonAddressEntity personAddress;

}
