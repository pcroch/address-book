package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.QRCodeEntity;
import api.addressbook.repository.QRCodeRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on PersonAddress endpoints ")
class QRCodeControllerTest {

    public static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QRCodeRepository qrcodeRepository;

    @Order(1)
    @Test
    @DisplayName("Ping the personAddress endpoint")
    void getPingPersonAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/qr-code/ping"))
                .andExpect(status().isAccepted());
    }

    @Order(2)
    @Test
    @DisplayName("testing get a QRCode per id ")
    void getAddressById() throws Exception {
        PersonAddressEntity personAddressEntity = new PersonAddressEntity(1,1,1);
        QRCodeEntity qrcodeEntity = new QRCodeEntity(1, "TestImage", new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20}, personAddressEntity);
        when(qrcodeRepository.findById(1)).thenReturn(Optional.of(qrcodeEntity));
        mockMvc.perform(MockMvcRequestBuilders.get("/qr-code/1"))
                .andExpect(status().isFound());
    }

}