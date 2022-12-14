package api.addressbook.contextLoads;

        import api.addressbook.controller.AddressController;
        import api.addressbook.controller.PersonAddressController;
        import api.addressbook.controller.PersonController;
        import api.addressbook.controller.QRCodeController;
        import api.addressbook.repository.PersonAddressRepository;
        import api.addressbook.repository.PersonRepository;
        import api.addressbook.repository.QRCodeRepository;
        import org.assertj.core.api.Assertions;
        import org.junit.jupiter.api.*;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.ActiveProfiles;
        import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing injection of auto-wired attributes")
class ApplicationContextLoadTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    AddressController addressController;

    @Autowired
    PersonController personController;

    @Autowired
    PersonAddressController personAddressController;

    @Autowired
    QRCodeController qrcodeController;

    @Order(1)
    @Test
    @DisplayName("Address Controller Context")
    void addressContextLoads() {
        Assertions.assertThat(addressController.hashCode()).isNotZero();
    }

    @Order(2)
    @Test
    @DisplayName("Person Controller Context")
    void personContextLoads() {
        Assertions.assertThat(personController.hashCode()).isNotZero();
    }

    @Order(3)
    @Test
    @DisplayName("PersonAddress Controller Context")
    void personAddressContextLoads() {
        Assertions.assertThat(personAddressController.hashCode()).isNotZero();
    }

    @Order(4)
    @Test
    @DisplayName("QrCode Controller Context")
    void qrcodeContextLoads() {
        Assertions.assertThat(qrcodeController.hashCode()).isNotZero();
    }
}