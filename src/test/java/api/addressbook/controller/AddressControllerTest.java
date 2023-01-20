package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.mapper.AddressMapper;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@ExtendWith(SpringExtension.class)
////@SpringBootTest
////@WebMvcTest
////@SpringBootTest
////@AutoConfigureMockMvc
//@SpringBootTest
//@AutoConfigureMockMvc
////@ExtendWith(MockitoExtension.class)



@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
@AutoConfigureMockMvc
@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Address endpoints ")
class AddressControllerTest {

    public static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AddressRepository addressRepository;

    private final PersonRepository personRepository;

    private final AddressMapper addressMapper;

    AddressControllerTest(PersonRepository personRepository, AddressMapper addressMapper) {
        this.personRepository = personRepository;
        this.addressMapper = addressMapper;
    }

//    @Mock
//    PersonRepository personRepository;

//    @BeforeEach
//    public void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(this.addressRepository = addressRepository)).build();
//    }

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(addressRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(addressRepository, personRepository, addressMapper)).build(); //new AddressController(), addressRepository
    }

//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }


    @Order(1)
    @Test
    @DisplayName("Ping the address endpoint")
    void getPing() throws Exception {
        mockMvc.perform(get("/address/ping"))
                .andExpect(status().isAccepted())
                   .andExpect(content().string("AddressController Ping"));

    }

    @Order(2)
    @Test
    @Disabled
    @DisplayName("testing get all address ")
    void getAllAddresses() throws Exception {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        AddressEntity addressEntity2 = new AddressEntity(2, "2", "A", "Test street 2", "2222", "Test City 2", "Test Country 2", true, null);
        addressEntityList.add(addressEntity1);
        logger.info("repo address {}", this.addressRepository);
        addressEntityList.add(addressEntity2);
        this.addressRepository.saveAll(addressEntityList);
//        doReturn(addressEntityList).when(addressRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/address"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false},{\"addressId\":2,\"streetNumber\":\"2\",\"boxNumber\":\"A\",\"streetName\":\"Test street 2\",\"zipcode\":\"2222\",\"locality\":\"Test City 2\",\"country\":\"Test Country 2\",\"personEntity\":null,\"private\":true}]"
                ));

    }

    @Order(3)
    @Test
    @Disabled
    @DisplayName("testing get a address per id ")
    void getAddressById() throws Exception {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity1));

        mockMvc.perform(get("/address/1"))
                .andExpect(status().isFound())
                .andExpect(content().json("{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}"));
    }

    @Order(4)
    @Test
    @Disabled
    @DisplayName("testing adding a address")
    void createAddress() throws Exception {
        String json = "{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}";
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity1);
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity1));
        mockMvc.perform(MockMvcRequestBuilders.post("/address/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}"));
    }

    @Order(5)
    @Test
    @Disabled
    @DisplayName("testing updating a address")
    void updateAddress() throws Exception {
        String json = "{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}";
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        when(addressRepository.save(addressEntity1)).thenReturn(addressEntity1);
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity1));
        mockMvc.perform(MockMvcRequestBuilders.put("/address/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted())
                .andExpect(content().json("{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}"));
    }

    @Test
    @Disabled
    void deleteAddress() {
//        Assert.assertTrue(true);
    }

    @Test
    @Disabled
    void deleteAddressPerID() {
//        Assert.assertTrue(true);
    }
}
