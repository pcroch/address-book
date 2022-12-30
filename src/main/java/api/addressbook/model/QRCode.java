package api.addressbook.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Data
@ToString
public class QRCode implements Serializable {
    private Integer personAddressId;
    private String qrCodeName;
    private byte[] qrCodeImage;
    private PersonAddress personAddress;
}