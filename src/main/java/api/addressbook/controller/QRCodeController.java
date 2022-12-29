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
import com.google.zxing.WriterException;
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

import java.io.*;
import java.util.Optional;

import static api.addressbook.service.AddressService.*;
import static api.addressbook.service.QRCodeGeneratorService.generateQRCodeImage;

@Controller
@RequestMapping("/qr-code")
public class QRCodeController {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);

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
    @RequestMapping("/id={person_address_id}")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCodeById(@PathVariable("person_address_id") Integer personAddressId) {

        Optional<QRCodeEntity> qrcodeEntity = qrcodeRepository.findById(personAddressId);
        return qrcodeEntity.map(qrCodeEntity -> ResponseEntity.status(HttpStatus.FOUND)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(qrCodeEntity.getQrCodeImage())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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

    @RequestMapping("/{addressId}/{personId}")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> createQRCodePerPersonAndAddress(@PathVariable("addressId") int addressId, @PathVariable("personId") int personId) throws IOException, WriterException {

        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        Optional<PersonEntity> personEntity = personRepository.findById(personId);
        PersonAddressEntity personAddressEntity = personAddressRepository.findByPersonIdAndAddressId(personId, addressId);

        if (personEntity.isEmpty() || addressEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        byte[] qrCodeImage = generateQRCodeImage(concatAddress(addressEntity.get()), 125, 125);
        String qrCodeName = QRCodeGeneratorService.generateQRCodeName(personEntity.get(), addressEntity.get());

        if (qrcodeRepository.existsByQrCodeName(qrCodeName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        QRCodeEntity qrcodeEntity = new QRCodeEntity(personAddressEntity.getPersonAddressId(),
                qrCodeName,
                qrCodeImage,
                personAddressEntity);
        logger.info("qrcode saved {}", qrcodeRepository.save(qrcodeEntity));

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(qrCodeImage);

    }
}


// part 2 b

//adding Unit testing and integration testing
// part 3
// improving the readme to explain a bit more the api
// see for versionning othe the api like v1 , v2 etc...

// part 4 a

// adding a search algorithm

// part 4 b

// refactoring the whole app for duplicate codes

// part 5 start a new project with camunda?