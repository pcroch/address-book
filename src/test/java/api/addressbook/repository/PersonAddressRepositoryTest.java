package api.addressbook.repository;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonAddressEntity;
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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on PersonAddress Repository ")
@Disabled //todo foreign key error when running the testing
class PersonAddressRepositoryTest extends AbstractRepository {

    private PersonAddressEntity personAddress1, personAddress2, personAddress3;

    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        addressRepository.deleteAll();
        personRepository.deleteAll();

        PersonEntity person1 = new PersonEntity(1, "Joe", "aaa", "aaaa", null);
        PersonEntity person2 = new PersonEntity(2, "Jane", "aaa", "aaaa", null);

        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(person1);
        personEntityList.add(person2);
        personRepository.saveAll(personEntityList);

        AddressEntity address1 = new AddressEntity(1, "1", "4B", "rue du trone", "1000", "Bruxelles", "Belgium", false, null);
        AddressEntity address2 = new AddressEntity(2, "12", null, "rue du roi", "5852", "Namur", "Belgium", false, null);

        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(address1);
        addressEntityList.add(address2);
        addressRepository.saveAll(addressEntityList);

        personAddress1 = new PersonAddressEntity(1, address2.getAddressId(), person1.getPersonId());
        personAddress2 = new PersonAddressEntity(2, address1.getAddressId(), person1.getPersonId());
        personAddress3 = new PersonAddressEntity(3, address1.getAddressId(), person2.getPersonId());

        List<PersonAddressEntity> personAddressEntityList = new ArrayList<>();
        personAddressEntityList.add(personAddress1);
        personAddressEntityList.add(personAddress2);
        personAddressRepository.saveAll(personAddressEntityList);
        repositoryCount = personAddressRepository.findAll().size();
    }

    @DisplayName("Save a person")
    @Test
    @Order(1)
    void test_save_person_repository() {
        PersonAddressEntity personAddressSaved = personAddressRepository.save(personAddress3);
        Assertions.assertThat(personAddressRepository.count()).isEqualTo(repositoryCount + 1);
        assertEquals(personAddress3.getPersonId(), personAddressSaved.getPersonId());
    }

    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
       int id = personAddressRepository.save(personAddress3).getPersonId();
        PersonAddressEntity personAddressSaved = personAddressRepository.findById(id).get();
        assertEquals(id, personAddressSaved.getPersonId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonAddressEntity> personAddressEntityList;
        personAddressEntityList = personAddressRepository.findAll();
        assertEquals(repositoryCount, personAddressEntityList.size());
        assertEquals(personAddress1.getPersonId(), personAddressEntityList.get(0).getPersonId());
        assertEquals(personAddress2.getPersonId(), personAddressEntityList.get(1).getPersonId());
    }

    @DisplayName("Update person's field")
    @Test
    @Order(4)
    @Rollback
    void test_update_person_repository() {
        personAddress1.setPersonId(3);
        PersonAddressEntity personAddressUpdated = personAddressRepository.save(personAddress1);
        assertEquals(3, personAddressUpdated.getPersonId());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback
    void test_delete_person_repository() {
        int id = personAddressRepository.save(personAddress3).getPersonId();
        PersonAddressEntity personAddressSaved = personAddressRepository.findById(id).get();
        personAddressRepository.delete(personAddressSaved);

        PersonAddressEntity personAddressEntity2 = null;
        Optional<PersonAddressEntity> optionalPersonAddress2 = personAddressRepository.findById(1);

        if (optionalPersonAddress2.isPresent()) {
            personAddressEntity2 = optionalPersonAddress2.get();
        }
        Assertions.assertThat(personAddressEntity2).isNull();
    }

    @DisplayName("Delete all persons")
    @Test
    @Order(6)
    @Rollback
    void test_delete_all_person_repository() {
        personAddressRepository.deleteAll();
        assertEquals(0, personAddressRepository.count());
    }
}
