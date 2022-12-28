package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.PersonEntity;
import api.addressbook.entity.QRCodeEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonAddressRepository;
import api.addressbook.repository.PersonRepository;
import api.addressbook.repository.QRCodeRepository;
import api.addressbook.service.QRCodeGeneratorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

import static api.addressbook.service.AddressService.*;
import static api.addressbook.service.QRCodeGeneratorService.generateQRCodeImage;

@Controller
@RequestMapping("/qr-code")
public class QRCodeController {

    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    @Autowired
    private QRCodeRepository qrcodeRepository;

    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private QRCodeGeneratorService qrcodeGeneratorService;

    @RequestMapping("/ping")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> getPingPersonAddress() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>("You have reached on QRCode Endpoints", headers, HttpStatus.ACCEPTED);
    }

    /**
     * It will get a  QR code based on qr_code id
     */
    @RequestMapping("/")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCodeById() throws IOException {
        File f = new File("src/main/resources/images/code.png");
        logger.info("here is get: {}", f.getAbsolutePath());
        FileInputStream fl = new FileInputStream(f);
        var byteArray = fl.readAllBytes();
        logger.info("byteArray {}", byteArray);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(byteArray);
    }

    @RequestMapping("/{addressId}/{personId}")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> createQRCodePerPersonAndAddress(@PathVariable("addressId") int addressId, @PathVariable("personId") int personId) throws IOException, WriterException {
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        logger.info("address {}", addressEntity);
        byte[] qrCodeImage = generateQRCodeImage(concatAddress(addressEntity), 125, 125);
        PersonAddressEntity personAddressEntity = personAddressRepository.findByPersonIdAndAddressId(personId, addressId);
        QRCodeEntity qrcodeEntity = new QRCodeEntity(personAddressEntity.getPersonAddressId(),
                QRCodeGeneratorService.generateQRCodeName(personRepository.findById(personId), addressEntity),
                qrCodeImage,
                personAddressEntity);
        qrcodeRepository.save(qrcodeEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(qrCodeImage);

        // custom error if name not unique
    }

    @RequestMapping("/findByName={qrCodeName}")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> findQRCodeByName(@PathVariable("qrCodeName") String qrCodeName) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                    body(qrcodeRepository.findByQrCodeName(qrCodeName).getQrCodeImage());
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}


// part 2
// gnerate a nice concat of the addres to writte it by hand

// part 3
// improving the readme to explain a bit more the api
// see for versionning othe the api like v1 , v2 etc...