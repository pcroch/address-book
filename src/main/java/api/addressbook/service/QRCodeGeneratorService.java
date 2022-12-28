package api.addressbook.service;


import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonAddressRepository;
import api.addressbook.repository.PersonRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;

@Service("QRCodeGeneratorService")
public class QRCodeGeneratorService {

    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        logger.info("byteArray {}", out.toByteArray());
        return out.toByteArray();
    }

    public static String generateQRCodeName(Optional<PersonEntity> personEntity, Optional<AddressEntity> addressEntity) {
        return String.format("%s_%s_%s",
                personEntity.get().getFirstname(),
                personEntity.get().getLastname(),
                addressEntity.get().getCountry());
    }
}