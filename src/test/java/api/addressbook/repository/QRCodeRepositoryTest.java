//package api.addressbook.repository;
//
//import api.addressbook.entity.AddressEntity;
//import api.addressbook.entity.PersonAddressEntity;
//import api.addressbook.entity.PersonEntity;
//import api.addressbook.entity.QRCodeEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Slf4j
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DisplayName("Unit Testing on Qr_code Repository ")
//class QRCodeRepositoryTest extends AbstractRepository {
//
//    private QRCodeEntity qrcode1, qrcode2, qrcode3;
//    private long qrcodeRepositoryCount;
//
//    @Autowired
//    private QRCodeRepository qrcodeRepository;
//
//    @Autowired
//    private PersonAddressRepository personAddressRepository;
//
//    @Autowired
//    private PersonRepository personRepository;
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @BeforeEach
//    public void setUp() {
//        log.info("addressRepository.count() {}", addressRepository.count());
////        addressRepository.deleteAll();
////        personRepository.deleteAll();
//        qrcodeRepository.deleteAll();
//
//        PersonEntity person1 = new PersonEntity(1, "Joe", "aaa", "aaaa", null);
//        personRepository.save(person1);
//
//        AddressEntity address1 = new AddressEntity(1, "1", "4B", "rue du trone", "1000", "Bruxelles", "Belgium", false, null);
//        addressRepository.save(address1);
//
//
//        PersonAddressEntity personAddress1 = new PersonAddressEntity(1, address1.getAddressId(), person1.getPersonId());
////        PersonAddressEntity personAddress2 = new PersonAddressEntity(2, address1.getAddressId(), person1.getPersonId());
////        PersonAddressEntity personAddress3 = new PersonAddressEntity(3, address1.getAddressId(), person1.getPersonId());
//
//        List<PersonAddressEntity> personAddressEntityList = new ArrayList<>();
//        personAddressEntityList.add(personAddress1);
////        personAddressEntityList.add(personAddress2);
////        personAddressEntityList.add(personAddress3);
//        personAddressRepository.saveAll(personAddressEntityList);
//
//        byte[] qrCodeImage = new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
//
//        qrcode1 = new QRCodeEntity(personAddress1.getPersonId(), "Code1", qrCodeImage, null);
////        qrcode2 = new QRCodeEntity(2, "Code2", qrCodeImage, null);
////        qrcode3 = new QRCodeEntity(2, "Code3", qrCodeImage, null);
//        List<QRCodeEntity> qrcodeEntityList = new ArrayList<>();
//        qrcodeEntityList.add(qrcode1);
////        qrcodeEntityList.add(qrcode2);
//        qrcodeRepository.saveAll(qrcodeEntityList);
//        qrcodeRepositoryCount = qrcodeRepository.count();
//
//    }
//
//    @DisplayName("Save a QR-Code")
//    @Test
//    @Order(1)
//    @Rollback
//    void test_save_qr_code_repository() {
//        QRCodeEntity qrcodeSaved = qrcodeRepository.save(qrcode3);
//        Assertions.assertThat(qrcodeRepository.count()).isEqualTo(qrcodeRepositoryCount + 1);
//        assertEquals(qrcode3.getQrCodeName(), qrcodeSaved.getQrCodeName());
//    }
//
//    @DisplayName("Get a QR-Code")
//    @Test
//    @Order(2)
//    @Rollback
//    void test_get_qr_code_repository() {
//        int id = qrcodeRepository.save(qrcode3).getPersonAddressId();
//        QRCodeEntity qrcodeSaved = qrcodeRepository.findById(id).get();
//        assertEquals(qrcode3.getQrCodeName(), qrcodeSaved.getQrCodeName());
//
//    }
//
//    @DisplayName("Delete a specific QR-Code")
//    @Test
//    @Order(3)
//    @Rollback
//    void test_delete_qr_code_repository() {
//        QRCodeEntity qrcodeSaved = qrcodeRepository.save(qrcode3);
//        qrcodeRepository.delete(qrcodeSaved);
//
//        QRCodeEntity qrcode1 = null;
//        Optional<QRCodeEntity> optionalQrcode1 = qrcodeRepository.findById(1);
//
//        if (optionalQrcode1.isPresent()) {
//            qrcode1 = optionalQrcode1.get();
//        }
//        Assertions.assertThat(qrcode1).isNull();
//    }
//}
