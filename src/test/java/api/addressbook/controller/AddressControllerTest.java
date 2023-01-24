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


/**
 * I wanted to use mockito for learning purpose, but it implies  big issue as I mock the DB whence I can not test the http method delete
 * Note that the profile test is useless here ad I mock all the interactions with the Repository
 */

@SpringBootTest
@Slf4j
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Address endpoints ")
class AddressControllerTest {

    /**
     * Note that in this test Class the "delete" endpoints are not properly tested
     * On my point of view: those specifics tests are useless
     * I keep them to remember to work of them
     * The problem comes from the Mocking of  the repo
     */

    private Address address1, address2, addressSaved = Address.builder().build();
    private String json;

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
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(
                addressRepository,
                personRepository,
                addressMapper)
        ).build();
        address1 = Address.builder()
                .addressId(1)
                .streetNumber("1")
                .boxNumber(null)
                .streetName("test street")
                .zipcode("1111")
                .locality("Test City")
                .country("Test Country")
                .isPrivate(false)
                .personAddressEntity(null).build();
        address2 = Address.builder()
                .addressId(2)
                .streetNumber("2")
                .boxNumber(null)
                .streetName("test street2")
                .zipcode("2222")
                .locality("Test City2")
                .country("Test Country2")
                .isPrivate(true)
                .personAddressEntity(null).build();

        json = "{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}";
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
    @DisplayName("get all address ")
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
    @DisplayName("get a address per id ")
    void getAddressById() throws Exception {
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressMapper.toMap(address1)));
        mockMvc.perform(MockMvcRequestBuilders.get("/address/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$['streetNumber']", Matchers.is("1")));
    }

    @Order(4)
    @Test
    @DisplayName("adding a address")
    void createAddress() throws Exception {
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressMapper.toMap(address1));
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressMapper.toMap(address1)));
        mockMvc.perform(MockMvcRequestBuilders.post("/address/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['streetNumber']", Matchers.is("1")));
    }

    @Order(5)
    @Test
    @DisplayName("updating a address")
    void updateAddress() throws Exception {
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressMapper.toMap(address1));
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressMapper.toMap(address1)));
        mockMvc.perform(MockMvcRequestBuilders.put("/address/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$['streetNumber']", Matchers.is("1")));
    }

    @Order(6)
    @Test
    @DisplayName("delete a specific address but not found")
    void deleteAddressPerIDWhenNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Order(7)
    @Test
    @Disabled
    @DisplayName("delete a specific address and it is found")
    void deleteAddressPerIdWithContent() throws Exception {
//        Mockito.doNothing().when(addressRepository).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/{id}", 1))
                .andExpect(status().isNoContent());
    }


    @Order(8)
    @Test
    @DisplayName("delete all addresses but empty repository")
    void deleteAllAddressesWithNoContent() throws Exception {
        when(addressRepository.count()).thenReturn((long) 0);
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/deleteAll"))
                .andExpect(status().isNoContent());

    }

    @Order(9)
    @Test
    @Disabled
    @DisplayName("delete all addresses")
    void deleteAllAddresses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/deleteAll"))
                .andExpect(status().isAccepted());

    }
}
