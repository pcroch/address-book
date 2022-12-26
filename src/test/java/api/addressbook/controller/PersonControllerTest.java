package api.addressbook.controller;

import api.addressbook.entity.PersonEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//todo check why migration 5 is not imported and postman is empty

/**
 * Documentation found in
 * <a href="https://reflectoring.io/spring-boot-test/">...</a>
 */
//@SpringBootTest
//@ExtendWith(SpringExtension.class)

//@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Person endpoints ")
class PersonControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);
    @Autowired
    private MockMvc mockMvc; // the error is normal

    @MockBean
    PersonController personController;

    @MockBean
    PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(this.personRepository = personRepository)).build();
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
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin", null);
        PersonEntity personEntity2 = new PersonEntity(2, "Test", "nom", "Fin", null);
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
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin", null);
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
        PersonEntity personEntity3 = new PersonEntity(1, "Alpha", "Beta", "Gamma",null);
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity3);
        when(personRepository.findById(1)).thenReturn(Optional.of(personEntity3));
        mockMvc.perform(MockMvcRequestBuilders.post("/person/")
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
        PersonEntity personEntity3 = new PersonEntity(1, "Alpha", "Beta", "Gamma", null);
        when(personRepository.save(personEntity3)).thenReturn(personEntity3);
        when(personRepository.findById(1)).thenReturn(Optional.of(personEntity3));
        mockMvc.perform(MockMvcRequestBuilders.put("/person/{id}", 1)
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
