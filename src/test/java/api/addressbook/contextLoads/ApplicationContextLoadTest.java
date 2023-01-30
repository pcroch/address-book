package api.addressbook.contextLoads;

import api.addressbook.controller.AddressController;
import api.addressbook.controller.PersonAddressController;
import api.addressbook.controller.PersonController;
import api.addressbook.controller.QRCodeController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing injection of auto-wired attributes")
class ApplicationContextLoadTest {

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