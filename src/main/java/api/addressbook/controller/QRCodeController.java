package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.mapper.PersonAddressMapper;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.mapper.QRCodeMapper;
import api.addressbook.model.Person;
import api.addressbook.model.PersonAddress;
import api.addressbook.model.QRCode;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonAddressRepository;
import api.addressbook.repository.PersonRepository;
import api.addressbook.repository.QRCodeRepository;
import api.addressbook.service.QRCodeGeneratorService;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Optional;

import static api.addressbook.service.AddressService.concatAddress;
import static api.addressbook.service.QRCodeGeneratorService.generateQRCodeImage;

@Controller
@Slf4j
@RequestMapping("/qr-code")
public class QRCodeController extends AbstractController {


    private final PersonMapper personMapper;
    private final QRCodeMapper qrcodeMapper;

    private final PersonAddressMapper personAddressMapper;

    private final AddressRepository addressRepository;
    private final QRCodeRepository qrcodeRepository;
    private final PersonRepository personRepository;

    private final PersonAddressRepository personAddressRepository;

    @Autowired
    public QRCodeController(PersonMapper personMapper, QRCodeMapper qrcodeMapper, PersonAddressMapper personAddressMapper, AddressRepository addressRepository, QRCodeRepository qrcodeRepository, PersonRepository personRepository, PersonAddressRepository personAddressRepository) {
        this.personMapper = personMapper;
        this.qrcodeMapper = qrcodeMapper;
        this.personAddressMapper = personAddressMapper;
        this.addressRepository = addressRepository;
        this.qrcodeRepository = qrcodeRepository;
        this.personRepository = personRepository;
        this.personAddressRepository = personAddressRepository;
    }

    /**
     * It will get a  QR code based on qr_code id
     */
    @RequestMapping("/id={person_address_id}")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCodeById(@PathVariable("person_address_id") int personAddressId) {

        Optional<QRCode> qrcode = qrcodeRepository.findById(personAddressId).map(qrcodeMapper::toDomain);
        return qrcode.map(qrCodeEntity -> ResponseEntity.status(HttpStatus.FOUND)
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                        .body(qrCodeEntity.getQrCodeImage()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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

        Optional<AddressEntity> address = addressRepository.findById(addressId);
        Optional<Person> person = personRepository.findById(personId).map(personMapper::toDomain);
        PersonAddress personAddress = personAddressMapper.toDomain(personAddressRepository.findByPersonIdAndAddressId(personId, addressId));
        if (person.isEmpty() || address.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        byte[] qrCodeImage = generateQRCodeImage(concatAddress(address.get()), 125, 125);
        String qrCodeName = QRCodeGeneratorService.generateQRCodeName(person.get(), address.get());

        if (qrcodeRepository.existsByQrCodeName(qrCodeName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        QRCode qrcode = QRCode.builder()
                .qrCodeImage(qrCodeImage)
                .qrCodeName(qrCodeName)
                .personAddress(personAddress)
                .build();
        log.info("qrcode saved {}", qrcodeRepository.save(qrcodeMapper.EntityToModel(qrcode)));

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(qrCodeImage);

    }
}