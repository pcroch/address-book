package api.addressbook.controller;

import api.addressbook.entity.QRCodeEntity;
import api.addressbook.mapper.PersonAddressMapper;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.mapper.QRCodeMapper;
import api.addressbook.model.PersonAddress;
import api.addressbook.model.QRCode;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonAddressRepository;
import api.addressbook.repository.PersonRepository;
import api.addressbook.repository.QRCodeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on PersonAddress endpoints ")
class QRCodeControllerTest {

    PersonAddress personAddress = PersonAddress.builder().build();
    QRCode qrcode = QRCode.builder().build();

    @Autowired
    private MockMvc mockMvc;

    private final PersonMapper personMapper;

    private final QRCodeMapper qrcodeMapper;

    private final PersonAddressMapper personAddressMapper;
    private final  AddressRepository addressRepository;
    private final QRCodeRepository qrcodeRepository;
    private final PersonRepository personRepository;

    private final PersonAddressRepository personAddressRepository;

//    @MockBean
//    QRCodeController qrcodeController;

    @Autowired
    public QRCodeControllerTest(PersonMapper personMapper, QRCodeMapper qrcodeMapper, PersonAddressMapper personAddressMapper, AddressRepository addressRepository, QRCodeRepository qrcodeRepository, PersonRepository personRepository, PersonAddressRepository personAddressRepository, QRCodeMapper qRCodeMapper) {
        this.personMapper = personMapper;
        this.qrcodeMapper = qrcodeMapper;
        this.personAddressMapper = personAddressMapper;
        this.addressRepository = addressRepository;
        this.qrcodeRepository = qrcodeRepository;
        this.personRepository = personRepository;
        this.personAddressRepository = personAddressRepository;
    }
    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new QRCodeController(personMapper, qrcodeMapper, personAddressMapper, addressRepository, qrcodeRepository, personRepository, personAddressRepository)).build();

//        personAddress = PersonAddress.builder()
//                .personId(1)
//                .addressId(1)
//                .personAddressId(1)
//                .build();

        qrcode = QRCode.builder()
                .qrCodeId(1)
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
        doReturn(test).when(qrcodeRepository).findById(test.get().getQrCodeId());
        mockMvc.perform(MockMvcRequestBuilders.get("/qr-code/1"))
                .andExpect(status().isFound());
    }
}