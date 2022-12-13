package com.api.addressbook.repository;

import com.api.addressbook.entity.PersonEntity;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@ActiveProfiles
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Person Repository ")
class PersonRepositoryTest {

    public static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    @Autowired
    private PersonRepository personRepository;


    @DisplayName("Save a person")
    @Test
    @Order(1)
    @Rollback(value = false) // not sure if it is usefully but good to keep in mind
    void test_save_person_repository() {
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        PersonEntity personEntitySaved1 = personRepository.save(personEntity1);
        assertEquals(1, personRepository.count());
        assertEquals(personEntity1.getPersonId(), personEntitySaved1.getPersonId());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_delete_person_repository() {
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        personRepository.save(personEntity1);
        PersonEntity personEntitySaved1 = personRepository.findById(1).get();
        assertEquals(personEntity1.getPersonId(), personEntitySaved1.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonEntity> personEntityList = new ArrayList<>();
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        PersonEntity personEntity2 = new PersonEntity(2, "Test", "nom", "Fin");
        personEntityList.add(personEntity1);
        personEntityList.add(personEntity2);
        personRepository.saveAll(personEntityList);
        personEntityList = personRepository.findAll();
        assertEquals(2, personEntityList.size());
        assertEquals(personEntity1.getPersonId(), personEntityList.get(0).getPersonId());
        assertEquals(personEntity2.getPersonId(), personEntityList.get(1).getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(4)
    @Rollback(value = false)
    void test_update_person_repository() {
        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        personRepository.save(personEntity1);
        personEntity1.setFirstname("John");
        PersonEntity personEntityUpdated = personRepository.save(personEntity1);
        assertEquals("John", personEntityUpdated.getFirstname());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteEmployeeTest() {

        PersonEntity personEntity1 = new PersonEntity(1, "Test", "nom", "Fin");
        personRepository.save(personEntity1);
        PersonEntity personEntitySaved1 = personRepository.findById(1).get();
        personRepository.delete(personEntitySaved1);

        PersonEntity personEntity2 = null;
        Optional<PersonEntity> optionalPerson2 = personRepository.findById(1);

        if (optionalPerson2.isPresent()) {
            personEntity2 = optionalPerson2.get();
        }
        Assertions.assertThat(personEntity2).isNull();
    }
}
