package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.QRCodeEntity;
import api.addressbook.mapper.QRCodeMapper;
import api.addressbook.model.Person;
import api.addressbook.model.PersonAddress;
import api.addressbook.model.QRCode;
import api.addressbook.repository.QRCodeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@Disabled
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on PersonAddress endpoints ")
class QRCodeControllerTest {

    PersonAddress personAddress = PersonAddress.builder().build();
    QRCode qrcode = QRCode.builder().build();
    public static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QRCodeRepository qrcodeRepository;

    @MockBean
    QRCodeController qrcodeController;
    @Mock
    private QRCodeMapper qRCodeMapper;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new QRCodeController()).build();

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
    @Order(1)
    @Test
    @DisplayName("Ping the personAddress endpoint")
    void getPingPersonAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/qr-code/ping"))
                .andExpect(status().isAccepted());
    }

    @Order(2)
    @Test
    @Disabled
    @DisplayName("testing get a QRCode per id ")
    void getAddressById() throws Exception {
        Optional<QRCodeEntity> test = Optional.of(new QRCodeEntity(1, "test", null, null));
        doReturn(test).when(qrcodeRepository).findById(test.get().getPersonAddressId());
        mockMvc.perform(MockMvcRequestBuilders.get("/qr-code/1"))
                .andExpect(status().isFound());
    }
}