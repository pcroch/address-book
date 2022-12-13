package com.api.addressbook.controller;

import com.api.addressbook.AddressBookApplication;
import com.api.addressbook.entity.PersonEntity;
import com.api.addressbook.repository.PersonRepository;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Documentation found in
 * <a href="https://reflectoring.io/spring-boot-test/">...</a>
 */
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@ActiveProfiles
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Person endpoints ")
class PersonControllerTest {

    public static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);
    @Autowired
    private MockMvc mockMvc; // the error is normal

    @MockBean
    PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup().build();
    }

    @Order(1)
    @Test

    @DisplayName("testing /person/ping")
    void getPing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person/ping"))
                .andExpect(status().isAccepted());
    }

    @Order(2)
    @Test
    @DisplayName("testing get all person ")
    void getAllPerson() throws Exception {
        List<PersonEntity> personEntityList = new ArrayList<>();
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        PersonEntity personEntity2 = new PersonEntity(2, "Test", "nom", "Fin");
        personEntityList.add(personEntity1);
        personEntityList.add(personEntity2);

        when(personRepository.findAll()).thenReturn(personEntityList);
        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"},{\"personId\":2,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}]"));
    }

    @Order(3)
    @Test
    @DisplayName("testing get a person per id ")
    void getPersonById() throws Exception {
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        when(personRepository.findById(1)).thenReturn(Optional.of(personEntity1));

        mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
                .andExpect(status().isFound())
                .andExpect(content().json("{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}"));
    }
/*
https://stackoverflow.com/questions/56246445/org-springframework-http-converter-httpmessagenotreadableexception-when-running
Could be the solution on body issue
 */
    @Order(4)
    @Test
    @DisplayName("testing adding a person")
    void create() throws Exception {
        String json = "{\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"} ";
        PersonEntity personEntity3 = new PersonEntity(1, "Alpha", "Beta", "Gamma");
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity3);
        when(personRepository.findById(1)).thenReturn(Optional.of(personEntity3));
        mockMvc.perform (MockMvcRequestBuilders.post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"personId\":1,\"firstname\":\"Alpha\",\"secondname\":\"Beta\",\"lastname\":\"Gamma\"}"));
    }

    @Order(5)
    @Test
    @DisplayName("testing updating a person")
    void update() throws Exception {
        String json = "{\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"} ";
        PersonEntity personEntity3 = new PersonEntity(1, "Alpha", "Beta", "Gamma");
        when(personRepository.save(personEntity3)).thenReturn(personEntity3);
        when(personRepository.findById(1)).thenReturn(Optional.of(personEntity3));
        mockMvc.perform (MockMvcRequestBuilders.put("/person/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted())
                .andExpect(content().json("{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}"));
    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void deletePerson() {
//    }
}
