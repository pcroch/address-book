package api.addressbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qr-code")
public class QRCodeEntity implements Serializable {

    @Id
    @Column(name = "person_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personAddressId;

    @Column(name = "qr_code_name")
    private String qrCodeName;

    @Column(name = "qr_code_image")
    private byte[] qrCodeImage;

//    @Transient
//    @OneToOne(cascade = CascadeType.ALL) // , mappedBy = "address"
//    private AddressPerson addressPerason;

}
