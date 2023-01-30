package api.addressbook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qr_code")
public class QRCodeEntity implements Serializable {

    @Id
    @Column(name = "qr_code_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer qrCodeId;

    @Column(name = "qr_code_name", unique=true)
    private String qrCodeName;

    @Column(name = "qr_code_image")
    private byte[] qrCodeImage;

//    @Transient
    @OneToOne(mappedBy = "qrcodeEntity")
    private PersonAddressEntity personAddressEntity;
}
