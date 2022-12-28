package api.addressbook.service;


import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonEntity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service("QRCodeGeneratorService")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QRCodeGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeGeneratorService.class);

    public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        logger.info("byteArray {}", out.toByteArray());
        return out.toByteArray();
    }

    public static String generateQRCodeName(PersonEntity personEntity, AddressEntity addressEntity) {
         return String.format("%s_%s_%s_%s_%s",
                personEntity.getFirstname(), //get().
                personEntity.getLastname(),
                personEntity.getPersonId(),
                addressEntity.getCountry(),
                addressEntity.getAddressId());
    }

}