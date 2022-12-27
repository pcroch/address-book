package api.addressbook.repository;

import api.addressbook.entity.PersonEntity;
import api.addressbook.entity.QRCodeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Qr_code Repository ")
class QRCodeRepositoryTest {

    public static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Autowired
    private QRCodeRepository qrcodeRepository;

    @DisplayName("Save a QR-Code")
    @Test
    @Order(1)
    @Rollback
    void test_save_qr_code_repository() {
        QRCodeEntity qrcodeEntity = new QRCodeEntity(1, "TestImage", new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20});
        QRCodeEntity qrcodeEntitySaved = qrcodeRepository.save(qrcodeEntity);
        Assertions.assertThat(qrcodeRepository.count()).isEqualTo(1L);
        assertEquals(qrcodeEntity.getQrCodeName(), qrcodeEntitySaved.getQrCodeName());
    }

    @DisplayName("Get a QR-Code")
    @Test
    @Order(1)
    @Rollback
    void test_get_qr_code_repository() {
        QRCodeEntity qrcodeEntity = new QRCodeEntity(1, "TestImage", new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20});
        Integer id = qrcodeRepository.save(qrcodeEntity).getPersonAddressId();
        QRCodeEntity qrcodeEntitySaved = qrcodeRepository.findById(id).get();
        assertEquals(qrcodeEntity.getQrCodeName(), qrcodeEntitySaved.getQrCodeName());

    }

    @DisplayName("Delete a specific QR-Code")
    @Test
    @Order(1)
    @Rollback
    void test_delete_qr_code_repository() {
        QRCodeEntity qrcodeEntity = new QRCodeEntity(1, "TestImage", new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20});
        QRCodeEntity qrcodeEntitySaved = qrcodeRepository.save(qrcodeEntity);
        qrcodeRepository.delete(qrcodeEntitySaved);

        QRCodeEntity qrcodeEntity1 = null;
        Optional<QRCodeEntity> optionalQrcodeEntity1 = qrcodeRepository.findById(1);

        if (optionalQrcodeEntity1.isPresent()) {
            qrcodeEntity1 = optionalQrcodeEntity1.get();
        }
        Assertions.assertThat(qrcodeEntity1).isNull();

    }


}
