package api.addressbook.controller;

import api.addressbook.mapper.PersonAddressMapper;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.mapper.QRCodeMapper;
import api.addressbook.model.PersonAddress;
import api.addressbook.model.QRCode;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonAddressRepository;
import api.addressbook.repository.PersonRepository;
import api.addressbook.repository.QRCodeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        qrcode = QRCode.builder()
                .qrCodeId(1)
                .qrCodeName("TestImage")
                .qrCodeImage(new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20})
                .personAddress(personAddress)
                .build();

        qrcodeRepository.save(qrcodeMapper.EntityToModel(qrcode));
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
    @Disabled  // i must add the data personAddress
    @DisplayName("Finding a QRCode per id ")
    void testGetQRCodeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/qr-code/{id}", qrcodeRepository.findAll().get(0).getQrCodeId()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$['qrCodeName']", Matchers.is("TestImage")));
    }

    @Order(3)
    @Test
    @Disabled  //todo i must add the data personAddress
    @DisplayName("Finding a QRCode by name ")
    void testFindQRCodeByName() throws Exception {
        // test
    }

    @Order(4)
    @Test
    @Disabled  //todo i must add the data personAddress
    @DisplayName("Creating a QRCode ")
    void testCreateQRCodePerPersonAndAddress() throws Exception {
        // test
    }
}