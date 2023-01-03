package api.addressbook.repository;

import api.addressbook.entity.PersonEntity;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.model.Address;
import api.addressbook.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * tuto with:
 * <a href="https://www.javaguides.net/2021/07/crud-junit-tests-for-spring-data-jpa.html">...</a>
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Person Repository ")
class PersonRepositoryTest {

//    @Autowired
    private PersonMapper personMapper;
    long  personRepositoryCount = 0;
    private PersonEntity person1;
    public static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        personRepositoryCount = personRepository.count();
//        personMapper = mock(PersonMapper.class);
//        address = Address.builder()
//                .addressId(1)
//                .streetName("1")
//                .boxNumber(null)
//                .streetName("test street")
//                .zipcode("1111")
//                .locality("Test City")
//                .country("Test Country")
//                .isPrivate(false)
//                .person(null)
//                .build();
//        addressSet.add(address);
//        person1 = Person.builder()
//                .personId(1)
//                .firstname("Test")
//                .lastname(null)
//                .lastname("Tester")
//                .address(null)
//                .build();
//        person2 = Person.builder()
//                .personId(1)
//                .firstname("Test2")
//                .lastname(null)
//                .lastname("Tester2")
//                .address(addressSet)
//                .build();
//        person3 = Person.builder()
//                .personId(1)
//                .firstname("Alpha")
//                .lastname("Beta")
//                .lastname("Gamma")
//                .address(addressSet)
//                .build();

        person1 = new PersonEntity(4, "Test", "aaa", "aaaa", null);
//        person4 = personMapper.toMap(person1);
    }

    @DisplayName("Save a person")
    @Test
    @Order(1)
    @Rollback
        // not sure if it is usefully bcse the rool back is automatic but good to keep in mind
    void test_save_person_repository() {

        PersonEntity personSaved = personRepository.save(person1);
        logger.info("list repo {}", personRepository.findAll());
        Assertions.assertThat(personRepository.count()).isEqualTo(personRepositoryCount + 1);
        assertEquals(person1.getFirstname(), personSaved.getFirstname());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
//       int id = personRepository.save(personMapper.toMap(person1)).getPersonId();
        PersonEntity personSaved1 = personRepository.findById(1).get();
        assertEquals(1, personSaved1.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonEntity> personEntityList = new ArrayList<>();
//        personEntityList.add(personMapper.toMap(person1));
//        personEntityList.add(personMapper.toMap(person2));
//        personRepository.saveAll(personEntityList);
        personEntityList = personRepository.findAll();
        assertEquals(personRepositoryCount, personEntityList.size());
        assertEquals(1, personEntityList.get(0).getPersonId());
        assertEquals(2, personEntityList.get(1).getPersonId());
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
    void deletePersonTest() {
        PersonEntity personSaved1 = personRepository.findById(1).get();
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
        personRepository.deleteAll();
        assertEquals(0, personRepository.count());
    }
}
