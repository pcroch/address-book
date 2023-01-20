package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.mapper.AddressMapper;
import api.addressbook.model.Address;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@Transactional
//@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Address endpoints ")
class AddressControllerTest {

    private Address address1, address2, addressSaved = Address.builder().build();

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private final AddressRepository addressRepository;

    private final PersonRepository personRepository;

    private final AddressMapper addressMapper;

    @Autowired
    public AddressControllerTest(AddressRepository addressRepository, PersonRepository personRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
        this.addressMapper = addressMapper;
    }

    @BeforeEach
    public void init() {
//        MockitoAnnotations.openMocks(addressRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(
                addressRepository,
                personRepository,
                addressMapper)
        ).build();
        addressRepository.deleteAll();
        address1 = Address.builder()
                .addressId(1)
                .streetNumber("1")
                .boxNumber(null)
                .streetName("test street")
                .zipcode("1111")
                .locality("Test City")
                .country("Test Country")
                .isPrivate(false)
                .person(null).build();
        address2 = Address.builder()
                .addressId(2)
                .streetNumber("2")
                .boxNumber(null)
                .streetName("test street2")
                .zipcode("2222")
                .locality("Test City2")
                .country("Test Country2")
                .isPrivate(true)
                .person(null).build();
    }


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
    @DisplayName("testing get all address ")
    @Rollback
    void getAllAddresses() throws Exception {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(addressMapper.toMap(address1));
        addressEntityList.add(addressMapper.toMap(address2));

        when(addressRepository.findAll()).thenReturn(addressEntityList);
        mockMvc.perform(MockMvcRequestBuilders.get("/address"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].streetNumber", Matchers.is("1")));
    }
    @Order(3)
    @Test
    @DisplayName("testing get a address per id ")
    void getAddressById() throws Exception {
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressMapper.toMap(address1)));
        log.info("optionaaaaa {}", addressRepository.findById(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/address/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$['streetNumber']",Matchers.is("1")));
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
