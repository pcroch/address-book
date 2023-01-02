package api.addressbook.controller;

import api.addressbook.entity.PersonEntity;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.model.Address;
import api.addressbook.model.Person;
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
import java.util.stream.Collectors;

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

    private Address address1, address2, addressSaved = Address.builder().build();
    private Person person1, person2, person3, personSaved = Person.builder().build();
    public static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);
    @Autowired
    private MockMvc mockMvc; // the error is normal

    @MockBean
    PersonController personController;

    @MockBean
    PersonRepository personRepository;
    @Autowired
    private PersonMapper personMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(this.personRepository = personRepository)).build();
    }

    @BeforeAll
    public void prepareMock(){

        person1 = Person.builder()
                .personId(1)
                .firstname("Test")
                .lastname(null)
                .lastname("Tester")
                .address(null)
                .build();
        person2 = Person.builder()
                .personId(1)
                .firstname("Test2")
                .lastname(null)
                .lastname("Tester2")
                .address(null)
                .build();
        person3 = Person.builder()
                .personId(1)
                .firstname("Alpha")
                .lastname("Beta")
                .lastname("Gamma")
                .address(null)
                .build();
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
        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);

        when(personRepository.findAll()).thenReturn(personList.stream().map(personMapper::toMap).collect(Collectors.toList()));
        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"personId\":1,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"},{\"personId\":2,\"firstname\":\"Test\",\"secondname\":\"nom\",\"lastname\":\"Fin\"}]"));
    }

    @Order(3)
    @Test
    @DisplayName("testing get a person per id ")
    void getPersonById() throws Exception {
        when(personRepository.findById(1)).thenReturn(Optional.of(person1).map(personMapper::toMap));
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
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personMapper.toMap(person3));
        when(personRepository.findById(1)).thenReturn(Optional.of(personMapper.toMap(person3)));
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
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personMapper.toMap(person3));
        when(personRepository.findById(1)).thenReturn(Optional.of(personMapper.toMap(person3)));
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
