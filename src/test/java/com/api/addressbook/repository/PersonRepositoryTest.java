package com.api.addressbook.repository;

import com.api.addressbook.entity.PersonEntity;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
import static org.junit.jupiter.api.Assertions.assertTimeout;

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

    public static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    @Autowired
    private PersonRepository personRepository;

    @DisplayName("Save a person")
    @Test
    @Order(1)
    @Rollback(value = true) // not sure if it is usefully bcse the rool back is automatic but good to keep in mind
    void test_save_person_repository() {
        PersonEntity personEntity1 = new PersonEntity(null, "Alpha", "Beta", "Gamma");
        PersonEntity personEntitySaved = personRepository.save(personEntity1);
        Assertions.assertThat(personRepository.count()).isEqualTo(1L);
        assertEquals(personEntity1.getFirstname(), personEntitySaved.getFirstname());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
        PersonEntity personEntity1 = new PersonEntity(null,"Test", "nom", "Fin");
        Integer id = personRepository.save(personEntity1).getPersonId();
        PersonEntity personEntitySaved1 = personRepository.findById(id).get();
        assertEquals(personEntity1.getPersonId(), personEntitySaved1.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonEntity> personEntityList = new ArrayList<>();;
        PersonEntity personEntity1 = new PersonEntity(null, "Test", "nom", "Fin");
        PersonEntity personEntity2 = new PersonEntity(null, "Test", "nom", "Fin");
        personEntityList.add(personEntity1);
        personEntityList.add(personEntity2);
        personRepository.saveAll(personEntityList);
        personEntityList = personRepository.findAll();
        assertEquals(2, personEntityList.size());
        assertEquals(personEntity1.getPersonId(), personEntityList.get(0).getPersonId());
        assertEquals(personEntity2.getPersonId(), personEntityList.get(1).getPersonId());
    }

    @DisplayName("Update person's field")
    @Test
    @Order(4)
    @Rollback(value = true)
    void test_update_person_repository() {
        PersonEntity personEntity1 = new PersonEntity(null, "Test", "nom", "Fin");
        personRepository.save(personEntity1);
        personEntity1.setFirstname("John");
        PersonEntity personEntityUpdated = personRepository.save(personEntity1);
        assertEquals("John", personEntityUpdated.getFirstname());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback(value = true)
    void deletePersonTest() {
        PersonEntity personEntity1 = new PersonEntity(null, "Test", "nom", "Fin");
        PersonEntity personEntitySaved1 = personRepository.save(personEntity1);
        personRepository.delete(personEntitySaved1);

        PersonEntity personEntity2 = null;
        Optional<PersonEntity> optionalPerson2 = personRepository.findById(1);

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
        PersonEntity personEntity1 = new PersonEntity(null, "Test", "nom", "Fin");
        personRepository.save(personEntity1);
        personRepository.deleteAll();
        assertEquals(0, personRepository.count());
    }
}
