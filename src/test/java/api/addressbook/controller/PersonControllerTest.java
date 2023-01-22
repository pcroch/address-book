package api.addressbook.controller;

import api.addressbook.mapper.PersonMapper;
import api.addressbook.model.Person;
import api.addressbook.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Documentation found in
 * <a href="https://reflectoring.io/spring-boot-test/">...</a>
 * <p>
 * Note that I will change the way to test and use the testing db that I set up in the application-test.properties
 */


@SpringBootTest
@Slf4j
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration Testing on Person endpoints ")
class PersonControllerTest {

    private Person person1, person2 = Person.builder().build();
    private String json;
    private int repositoryCount;
    @Autowired
    private MockMvc mockMvc;

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Autowired
    public PersonControllerTest(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @BeforeEach
    public void init() {
        personRepository.deleteAll();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(
                personRepository,
                personMapper)
        ).build();
        person1 = Person.builder()
                .firstname("Test 1")
                .lastname(null)
                .lastname("Tester 1")
                .address(null)
                .build();
        personRepository.save(personMapper.toMap(person1));

        person2 = Person.builder()
                .firstname("Test 2")
                .lastname("Tester Name 2")
                .lastname("Tester 2")
                .address(null)
                .build();
        personRepository.save(personMapper.toMap(person2));

        repositoryCount = (int) personRepository.count();
        json = "{\"firstname\":\"Test Added 1\",\"secondname\":\"Test Name Added 1\",\"lastname\":\"Last NameAdded 1\", \"address\": null}";

    }

    @Order(1)
    @Test
    @DisplayName("/person/ping")
    void getPing() throws Exception {
        mockMvc.perform(get("/person/ping"))
                .andExpect(status().isAccepted());
    }

    @Order(2)
    @Test
    @Rollback
    @DisplayName("get all person ")
    void getAllPerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", Matchers.hasSize(repositoryCount)))
                .andExpect(jsonPath("$[0].firstname", Matchers.is("Test 1")));
    }

    @Order(3)
    @Test
    @Rollback
    @DisplayName("finding a person per id ")
    void getPersonByIdAndFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", personRepository.findAll().get(0).getPersonId()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$['firstname']", Matchers.is("Test 1")));
    }

    @Order(4)
    @Test
    @Rollback
    @DisplayName("a person with id is not found")
    void getPersonByIdAndNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", 9999999))
                .andExpect(status().isNotFound());
    }

    /**
     * https://stackoverflow.com/questions/56246445/org-springframework-http-converter-httpmessagenotreadableexception-when-running
     * Could be the solution on body issue
     **/
    @Order(5)
    @Test
    @Rollback
    @DisplayName("create a person")
    void createPerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['firstname']", Matchers.is("Test Added 1")));
    }

    @Order(6)
    @Test
    @Rollback
    @DisplayName("create a person with a bad request")
    void createBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Bad request String"))
                .andExpect(status().isBadRequest());
    }
//
    @Order(7)
    @Test
    @Rollback
    @DisplayName("updating a person's name")
    void updateWithPersonFound() throws Exception {
        String newJson = "{\"firstname\":\"New Name 1\",\"secondname\":\"Test Name Added 1\",\"lastname\":\"Last NameAdded 1\", \"address\": null}";
        int id =  personRepository.findAll().get(0).getPersonId();
        mockMvc.perform(MockMvcRequestBuilders.put("/person/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$['firstname']", Matchers.is(personRepository.findById(id).get().getFirstname())));
    }

    @Order(7)
    @Test
    @Rollback
    @DisplayName("person not found when  updating")
    void updateWithPersonNotFound() throws Exception {
        String newJson = "{\"firstname\":\"New Name 1\",\"secondname\":\"Test Name Added 1\",\"lastname\":\"Last NameAdded 1\", \"address\": null}";
        mockMvc.perform(MockMvcRequestBuilders.put("/person/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newJson))
                .andExpect(status().isNotFound());
    }

    @Order(8)
    @Test
    @Rollback
    @DisplayName("delete a specific person but not found")
    void deletePersonPerIDWhenNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/{id}", 9999999))
                .andExpect(status().isNotFound());
    }

    @Order(9)
    @Test
    @Rollback
    @DisplayName("delete a specific person when it is found")
    void deletePersonPerIdWithAFoundContent() throws Exception {
        int id =  personRepository.findAll().get(0).getPersonId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/{id}", id))
                .andExpect(status().isNoContent());
    }


    @Order(10)
    @Test
    @Rollback
    @DisplayName("delete all person but empty repository")
    void deleteAllAddressesWithNoContent() throws Exception {
        personRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/deleteAll"))
                .andExpect(status().isNoContent());

    }

    @Order(11)
    @Test
@Rollback
    @DisplayName("testing delete all persons")
    void deleteAllAddresses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/deleteAll"))
                .andExpect(status().isAccepted());
    }
}
