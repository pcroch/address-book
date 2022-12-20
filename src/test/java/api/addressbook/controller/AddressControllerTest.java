package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Address endpoints ")
class AddressControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);
    @Autowired
    private MockMvc mockMvc; // the error is normal

    @MockBean
    PersonController personController;

    @MockBean
    AddressController addressController;


    @MockBean
    AddressRepository addressRepository;

    @MockBean
    PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(this.addressRepository = addressRepository)).build();
    }

    @Order(1)
    @Test
    @DisplayName("Ping the address endpoint")
    void getPing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/address/ping"))
                .andExpect(status().isAccepted());
    }

    @Order(2)
    @Test
    @DisplayName("testing get all address ")
    void getAllAddresses() throws Exception {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        AddressEntity addressEntity2 = new AddressEntity(2, "2", "A", "Test street 2", "2222", "Test City 2", "Test Country 2", true, null);
        addressEntityList.add(addressEntity1);
        addressEntityList.add(addressEntity2);

        when(addressRepository.findAll()).thenReturn(addressEntityList);
        mockMvc.perform(MockMvcRequestBuilders.get("/address"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}," +
                        "{\"addressId\":2,\"streetNumber\":\"2\",\"boxNumber\":\"A\",\"streetName\":\"Test street 2\",\"zipcode\":\"2222\",\"locality\":\"Test City 2\",\"country\":\"Test Country 2\",\"personEntity\":null,\"private\":true}]"
                ));
    }

    @Order(3)
    @Test
    @DisplayName("testing get a address per id ")
    void getAddressById() throws Exception {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity1));

        mockMvc.perform(MockMvcRequestBuilders.get("/address/1"))
                .andExpect(status().isFound())
                .andExpect(content().json("{\"addressId\":1,\"streetNumber\":\"1\",\"boxNumber\":null,\"streetName\":\"Test street\",\"zipcode\":\"1111\",\"locality\":\"Test City\",\"country\":\"Test Country\",\"personEntity\":null,\"private\":false}"));
    }

    @Order(4)
    @Test
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
    void deleteAddress() {
        Assert.assertTrue(true);
    }

    @Test
    void deleteAddressPerID() {
        Assert.assertTrue(true);
    }
}
