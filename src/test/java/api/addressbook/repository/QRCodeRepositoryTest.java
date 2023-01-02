package api.addressbook.repository;

import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.QRCodeEntity;
import api.addressbook.mapper.QRCodeMapper;
import api.addressbook.model.PersonAddress;
import api.addressbook.model.QRCode;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.*;
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

    PersonAddress personAddress = PersonAddress.builder().build();
    QRCode qrcode = QRCode.builder().build();

    @Autowired
    private QRCodeRepository qrcodeRepository;
    @Autowired
    private QRCodeMapper qRCodeMapper;

    @BeforeEach
    public void setUp() {

        personAddress = PersonAddress.builder()
                .personId(1)
                .addressId(1)
                .personAddressId(1)
                .build();

        qrcode = QRCode.builder()
                .personAddressId(1)
                .qrCodeName("TestImage")
                .qrCodeImage(new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20})
                .personAddress(personAddress)
                .build();
    }

    @DisplayName("Save a QR-Code")
    @Test
    @Order(1)
    @Rollback
    void test_save_qr_code_repository() {
        QRCodeEntity qrcodeSaved = qrcodeRepository.save(qRCodeMapper.EntityToModel(qrcode));
        Assertions.assertThat(qrcodeRepository.count()).isEqualTo(1L);
        assertEquals(qrcode.getQrCodeName(), qrcodeSaved.getQrCodeName());
    }

    @DisplayName("Get a QR-Code")
    @Test
    @Order(2)
    @Rollback
    void test_get_qr_code_repository() {
        int id = qrcodeRepository.save(qRCodeMapper.EntityToModel(qrcode)).getPersonAddressId();
        QRCodeEntity qrcodeSaved = qrcodeRepository.findById(id).get();
        assertEquals(qrcode.getQrCodeName(), qrcodeSaved.getQrCodeName());

    }

    @DisplayName("Delete a specific QR-Code")
    @Test
    @Order(3)
    @Rollback
    void test_delete_qr_code_repository() {
        QRCodeEntity qrcodeSaved = qrcodeRepository.save(qRCodeMapper.EntityToModel(qrcode));
        qrcodeRepository.delete(qrcodeSaved);

        QRCodeEntity qrcode1 = null;
        Optional<QRCodeEntity> optionalQrcode1 = qrcodeRepository.findById(1);

        if (optionalQrcode1.isPresent()) {
            qrcode1 = optionalQrcode1.get();
        }
        Assertions.assertThat(qrcode1).isNull();
    }
}
