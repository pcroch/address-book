package api.addressbook.controller;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on PersonAddress endpoints ")
class PersonAddressControllerTest {

    public static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);
    @Autowired
    private MockMvc mockMvc; // the error is normal

    @Order(1)
    @Test
    @DisplayName("Ping the personAddress endpoint")
    void getPingPersonAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person-address/ping"))
                .andExpect(status().isAccepted());
    }

}