package api.addressbook.repository;

import api.addressbook.entity.PersonEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tuto with:
 * <a href="https://www.javaguides.net/2021/07/crud-junit-tests-for-spring-data-jpa.html">...</a>
 * and
 * <a href="https://howtodoinjava.com/spring-boot2/testing/spring-boot-2-junit-5/">...</a>
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Disabled
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Person Repository ")
class PersonRepositoryTest extends AbstractRepository {

    private PersonEntity person1, person2, person3;
    public static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();

        person1 = new PersonEntity(1, "Joe", "aaa", "aaaa", null);
        person2 = new PersonEntity(2, "Jane", "aaa", "aaaa", null);
        person3 = new PersonEntity(1, "Batman", "null", "Steve", null);

        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(person1);
        personEntityList.add(person2);
        personRepository.saveAll(personEntityList);
        repositoryCount = personRepository.count();
    }

    @DisplayName("Save a person")
    @Test
    @Order(1)
    void test_save_person_repository() {
        PersonEntity personSaved = personRepository.save(person3);
        Assertions.assertThat(personRepository.count()).isEqualTo(repositoryCount + 1);
        assertEquals(person3.getFirstname(), personSaved.getFirstname());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
       int id = personRepository.save(person3).getPersonId();
        PersonEntity personSaved1 = personRepository.findById(id).get();
        assertEquals(id, personSaved1.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonEntity> personEntityList;
        personEntityList = personRepository.findAll();
        assertEquals(repositoryCount, personEntityList.size());
        assertEquals(person1.getFirstname(), personEntityList.get(0).getFirstname());
        assertEquals(person2.getFirstname(), personEntityList.get(1).getFirstname());
    }

    @DisplayName("Update person's field")
    @Test
    @Order(4)
    @Rollback
    void test_update_person_repository() {
        person1.setFirstname("John");
        PersonEntity personUpdated = personRepository.save(person1);
        assertEquals("John", personUpdated.getFirstname());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback
    void test_delete_person_repository() {
        int id = personRepository.save(person3).getPersonId();
        PersonEntity personSaved1 = personRepository.findById(id).get();
        personRepository.delete(personSaved1);

        PersonEntity personEntity2 = null;
        Optional<PersonEntity> optionalPerson2 = personRepository.findById(1);

        if (optionalPerson2.isPresent()) {
            personEntity2 = optionalPerson2.get();
        }
        Assertions.assertThat(personEntity2).isNull();
    }

    @DisplayName("Delete all persons")
    @Test
    @Order(6)
    @Rollback(value = true)
    void test_delete_all_person_repository() {
        personRepository.deleteAll();
        assertEquals(0, personRepository.count());
    }
}
