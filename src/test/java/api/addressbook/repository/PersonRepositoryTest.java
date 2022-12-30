package api.addressbook.repository;

import api.addressbook.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tuto with:
 * <a href="https://www.javaguides.net/2021/07/crud-junit-tests-for-spring-data-jpa.html">...</a>
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Person Repository ")
class PersonRepositoryTest {

    private Person person1, person2, person3, personSaved = Person.builder().build();

    public static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp(){

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

    @DisplayName("Save a person")
    @Test
    @Order(1)
    @Rollback // not sure if it is usefully bcse the rool back is automatic but good to keep in mind
    void test_save_person_repository() {
        Person personSaved = personRepository.save(person1);
        Assertions.assertThat(personRepository.count()).isEqualTo(1L);
        assertEquals(person1.getFirstname(), personSaved.getFirstname());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
       Integer id = personRepository.save(person1).getPersonId();
        Person personSaved1 = personRepository.findById(id).get();
        assertEquals(person1.getPersonId(), personSaved1.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<Person> personEntityList = new ArrayList<>();
        personEntityList.add(person1);
        personEntityList.add(person2);
        personRepository.saveAll(personEntityList);
        personEntityList = personRepository.findAll();
        assertEquals(2, personEntityList.size());
        assertEquals(person1.getPersonId(), personEntityList.get(0).getPersonId());
        assertEquals(person2.getPersonId(), personEntityList.get(1).getPersonId());
    }

    @DisplayName("Update person's field")
    @Test
    @Order(4)
    @Rollback
    void test_update_person_repository() {
        personRepository.save(person1);
        person1.setFirstname("John");
        Person personUpdated = personRepository.save(person1);
        assertEquals("John", personUpdated.getFirstname());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback
    void deletePersonTest() {
        Person personSaved1 = personRepository.save(person1);
        personRepository.delete(personSaved1);

        Person personEntity2 = null;
        Optional<Person> optionalPerson2 = personRepository.findById(1);

        if (optionalPerson2.isPresent()) {
            personEntity2 = optionalPerson2.get();
        }
        Assertions.assertThat(personEntity2).isNull();
    }

    @DisplayName("Delete all persons")
    @Test
    @Order(5)
    @Rollback(value = true)
    void deleteAllPersonTest() {
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.deleteAll();
        assertEquals(0, personRepository.count());
    }
}
