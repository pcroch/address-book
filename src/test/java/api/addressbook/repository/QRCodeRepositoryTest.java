package api.addressbook.repository;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.PersonEntity;
import api.addressbook.entity.QRCodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Qr_code Repository ")
class QRCodeRepositoryTest extends AbstractRepository {

    private QRCodeEntity qrcode1, qrcode2, qrcode3;
    private long qrcodeRepositoryCount;

    private PersonAddressEntity PersonAddressEntity2;
    int personId, addressId, personAddressId, qrcodeId;
    private List<PersonAddressEntity> personAddressEntityList = new ArrayList<>();

    private List<QRCodeEntity> qrcodeEntityList = new ArrayList<>();

    @Autowired
    private QRCodeRepository qrcodeRepository;

    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        addressRepository.deleteAll();
        personRepository.deleteAll();
        personAddressRepository.deleteAll();
        qrcodeRepository.deleteAll();

        AddressEntity address1 = new AddressEntity(1, "1", "4B", "rue du trone", "1000", "Bruxelles", "Belgium", false, null);
        AddressEntity address2 = new AddressEntity(2, "12", null, "rue du roi", "5852", "Namur", "Belgium", false, null);

        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(address1);
        addressEntityList.add(address2);
        addressRepository.saveAll(addressEntityList);

        PersonEntity person1 = new PersonEntity(1, "Joe", "aaa", "aaaa", null);
        PersonEntity person2 = new PersonEntity(2, "Jane", "aaa", "aaaa", null);

        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(person1);
        personEntityList.add(person2);
        personRepository.saveAll(personEntityList);

        PersonAddressEntity personAddressEntity1 = new PersonAddressEntity(1, addressRepository.findAll().get(0), personRepository.findAll().get(0), null);
        PersonAddressEntity2 = new PersonAddressEntity(2, addressRepository.findAll().get(1), personRepository.findAll().get(1), null);

        List<PersonAddressEntity> tmpList = new ArrayList<>();
        tmpList.add(personAddressEntity1);
        tmpList.add(PersonAddressEntity2);
        personAddressEntityList = Lists.newArrayList(personAddressRepository.saveAll(tmpList));

        personId = personRepository.findAll().get(0).getPersonId();
        addressId = addressRepository.findAll().get(0).getAddressId();
        personAddressId = personAddressRepository.findAll().get(0).getPersonAddressId();
        log.info("personAddressRepository.findAll().get(0) {} ", personAddressRepository.findAll().get(0));
        log.info("personAddressIdpersonAddressId {} ", personAddressId);
        byte[] qrCodeImage = new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};

        qrcode1 = new QRCodeEntity(1, "Code1", qrCodeImage, personAddressRepository.findAll().get(0));
        qrcode2 = new QRCodeEntity(2, "Code2", qrCodeImage, personAddressRepository.findAll().get(0));
        qrcode3 = new QRCodeEntity(3, "Code3", qrCodeImage, personAddressRepository.findAll().get(0));
        log.info("qrcode1qrcode1 {} ", qrcode1);

        List<QRCodeEntity> tmpCodeList = new ArrayList<>();
        tmpCodeList.add(qrcode1);
        tmpCodeList.add(qrcode2);
        qrcodeEntityList = Lists.newArrayList((qrcodeRepository.saveAll(tmpCodeList)));
        log.info("qrcodeRepositoryqrcodeRepository {} ", qrcodeRepository.findAll());
//
        qrcodeId = qrcodeRepository.findAll().get(0).getQrCodeId();
        qrcodeRepositoryCount = qrcodeRepository.count();

    }

    @DisplayName("Save a QR-Code")
    @Test
    @Order(1)
    @Rollback
    void test_save_qr_code_repository() {
        QRCodeEntity qrcodeSaved = qrcodeRepository.save(qrcode3);
        Assertions.assertThat(qrcodeRepository.count()).isEqualTo(qrcodeRepositoryCount + 1);
        assertEquals(qrcode3.getQrCodeName(), qrcodeSaved.getQrCodeName());
    }

    @DisplayName("Get a QR-Code")
    @Test
    @Order(2)
    void test_get_qr_code_repository() {
        int id = qrcodeRepository.save(qrcode3).getQrCodeId();
        QRCodeEntity qrcodeSaved = qrcodeRepository.findById(id).get();
        assertEquals(qrcode3.getQrCodeName(), qrcodeSaved.getQrCodeName());
    }


    @DisplayName("Get list of qrcodes")
    @Test
    @Order(3)
    void test_findAll_qrcode_repository() {
        List<QRCodeEntity> qrcodeFindAll = qrcodeRepository.findAll();
        assertEquals(qrcodeRepositoryCount, qrcodeFindAll.size());
        assertEquals(qrcodeFindAll.get(0).getQrCodeName(), qrcodeEntityList.get(1).getQrCodeName());
        assertEquals(qrcodeFindAll.get(1).getQrCodeName(), qrcodeEntityList.get(0).getQrCodeName());
    }

//    @DisplayName("Save a QR-Code")
//    @Test
//    @Order(4)
//    @Rollback
//    void test_save_qr_code_repository() {
//        QRCodeEntity qrcodeSaved = qrcodeRepository.save(qrcode3);
//        Assertions.assertThat(qrcodeRepository.count()).isEqualTo(qrcodeRepositoryCount + 1);
//        assertEquals(qrcode3.getQrCodeName(), qrcodeSaved.getQrCodeName());
//    }

    @DisplayName("Update qrcode's field")
    @Test
    @Order(5)
    @Rollback
    void test_update_qr_code_repository() {
        QRCodeEntity qrcodeSaved = qrcodeRepository.findById(qrcodeId).get();
        qrcodeSaved.setQrCodeName("New Name");
        QRCodeEntity qrcodeUpdated = qrcodeRepository.save(qrcodeSaved);
        assertEquals("New Name", qrcodeUpdated.getQrCodeName());
    }


    @DisplayName("Delete a specific QR-Code")
    @Test
    @Order(6)
    @Rollback
    void test_delete_qr_code_repository() {
        QRCodeEntity qrcodeSaved = qrcodeRepository.findById(qrcodeId).get();
        qrcodeRepository.delete(qrcodeSaved);

        QRCodeEntity qrcode1 = null;
        Optional<QRCodeEntity> optionalQrcode1 = qrcodeRepository.findById(qrcodeSaved.getQrCodeId());

        if (optionalQrcode1.isPresent()) {
            qrcode1 = optionalQrcode1.get();
        }
        Assertions.assertThat(qrcode1).isNull();
    }

    @DisplayName("Delete all persons")
    @Test
    @Order(7)
    @Rollback
    void test_delete_all_person_repository() {
        qrcodeRepository.deleteAll();
        assertEquals(0, qrcodeRepository.count());
    }

    @DisplayName("Find a Qrcode with its name")
    @Test
    @Order(8)
    void test_find_by_name_qrcode_repository() {
        QRCodeEntity qrcodeSaved = qrcodeRepository.findByQrCodeName("Code1");
        assertEquals("Code1", qrcodeSaved.getQrCodeName());
    }

    @DisplayName("Find if a Qrcode exists")
    @Test
    @Order(9)
    void test_find_qrcode_repository() {
        boolean hasExistingCode = qrcodeRepository.existsByQrCodeName("Code1");
        assertTrue(hasExistingCode);
    }

}
