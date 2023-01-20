package api.addressbook.controller;

import api.addressbook.entity.PersonEntity;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.model.Person;
import api.addressbook.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

//todo check why migration 5 is not imported and postman is empty

/**
 * Documentation found in
 * <a href="https://reflectoring.io/spring-boot-test/">...</a>
 */

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@ActiveProfiles("test")
@WebMvcTest(PersonController.class)
@Disabled
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Person endpoints ")
class PersonControllerTest {

    private Person person1 = Person.builder().build();
    public static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);
    @Autowired
    private MockMvc mockMvc; // the error is normal

    @MockBean
    PersonController personController;

    @MockBean
    PersonRepository personRepository;

    @Mock
    PersonMapper personMapper;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(
                this.personRepository = personRepository, personMapper)).build();
        person1 = Person.builder()
                .personId(1)
                .firstname("Test")
                .lastname(null)
                .lastname("Tester")
                .address(null)
                .build();
    }

    @Order(1)
    @Test
    @DisplayName("testing /person/ping")
    void getPing() throws Exception {
        mockMvc.perform(get("/person/ping"))
                .andExpect(status().isAccepted());
    }

    @Order(2)
    @Test
    @Disabled
    @DisplayName("testing get all person ")
    void getAllPerson() throws Exception {
        List<PersonEntity> personEntityList = new ArrayList<>();
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin", null);
        PersonEntity personEntity2 = new PersonEntity(2, "Test", "nom", "Fin", null);
        personEntityList.add(personEntity1);
        personEntityList.add(personEntity2);

//        when(personRepository.findAll()).thenReturn(personEntityList);
//        when(personRepository.findAll().stream().map(personMapper::toDomain)).thenReturn(personEntityList.stream().map(personMapper::toDomain));
//        when(personMapper.toDomain(personEntity1)).thenReturn(personMapper.toDomain(personEntity1));
        logger.info("mockMvc + 1  {}", mockMvc);
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"},{\"personId\":2,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}]"));
    }

    @Order(3)
    @Test
    @Disabled
    @DisplayName("testing get a person per id ")
    void getPersonById() throws Exception {
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin", null);

        Optional<api.addressbook.model.Person> optionalPerson = Optional.ofNullable(person1);
//        when(personRepository.findById(1)).thenReturn(Optional.of(personEntity1));
        when(personRepository.findById(1).map(personMapper::toDomain)).thenReturn(optionalPerson);
        //  doReturn(Optional.ofNullable(personMapper.toDomain(personEntity1))).when(personRepository.findById(1).map(personMapper::toDomain));
        mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
                .andExpect(status().isFound())
                .andExpect(content().json("{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}"));
    }

    //
//    /*
//    https://stackoverflow.com/questions/56246445/org-springframework-http-converter-httpmessagenotreadableexception-when-running
//    Could be the solution on body issue
//     */
    @Order(4)
    @Test
    @Disabled
    @DisplayName("testing adding a person")
    void create() throws Exception {
        String json = "{\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"} ";
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personMapper.toMap(person1));
        when(personRepository.findById(1)).thenReturn(Optional.of(personMapper.toMap(person1)));
        mockMvc.perform(MockMvcRequestBuilders.post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"personId\":1,\"firstname\":\"Alpha\",\"secondname\":\"Beta\",\"lastname\":\"Gamma\"}"));
    }

    @Order(5)
    @Test
    @Disabled
    @DisplayName("testing updating a person")
    void update() throws Exception {
        String json = "{\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"} ";
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personMapper.toMap(person1));
        when(personRepository.findById(1)).thenReturn(Optional.of(personMapper.toMap(person1)));
        mockMvc.perform(MockMvcRequestBuilders.put("/person/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted())
                .andExpect(content().json("{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}"));
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    @Disabled
    void deletePerson() {
    }
}
