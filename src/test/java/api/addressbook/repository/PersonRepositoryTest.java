package api.addressbook.repository;

import api.addressbook.entity.PersonEntity;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tuto with:
 * <a href="https://www.javaguides.net/2021/07/crud-junit-tests-for-spring-data-jpa.html">...</a>
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Person Repository ")
class PersonRepositoryTest {

    @Autowired
    private PersonMapper personMapper;

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
        PersonEntity personSaved = personRepository.save(personMapper.toMap(person1));
        Assertions.assertThat(personRepository.count()).isEqualTo(1L);
        assertEquals(person1.getFirstname(), personSaved.getFirstname());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
       int id = personRepository.save(personMapper.toMap(person1)).getPersonId();
        PersonEntity personSaved1 = personRepository.findById(id).get();
        assertEquals(person1.getPersonId(), personSaved1.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(personMapper.toMap(person1));
        personEntityList.add(personMapper.toMap(person2));
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
        personRepository.save(personMapper.toMap(person1));
        person1.setFirstname("John");
        PersonEntity personUpdated = personRepository.save(personMapper.toMap(person1));
        assertEquals("John", personUpdated.getFirstname());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback
    void deletePersonTest() {
        PersonEntity personSaved1 = personRepository.save(personMapper.toMap(person1));
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
    void deleteAllPersonTest() {
        personRepository.save(personMapper.toMap(person1));
        personRepository.save(personMapper.toMap(person2));
        personRepository.deleteAll();
        assertEquals(0, personRepository.count());
    }
}
