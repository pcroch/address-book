package api.addressbook.repository;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on PersonAddress Repository ")
class PersonAddressRepositoryTest extends AbstractRepository {

    private PersonAddressEntity PersonAddressEntity2;
    int personId, addressId, personAddressId;
    private List<PersonAddressEntity> personAddressEntityList = new ArrayList<>();
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
        personAddressRepository.deleteAll();

        AddressEntity address1 = new AddressEntity(1, "1", "4B", "rue du trone", "1000", "Bruxelles", "Belgium", false, null);
        AddressEntity address2 = new AddressEntity(2, "12", null, "rue du roi", "5852", "Namur", "Belgium", false, null);

        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(address1);
        addressEntityList.add(address2);
        addressRepository.saveAll(addressEntityList);

        PersonEntity person1 = new PersonEntity(1, "Joe", "aaa", "aaaa", null);
        PersonEntity person2 = new PersonEntity(2, "Jane", "aaa", "aaaa", null);

        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(person1);
        personEntityList.add(person2);
        personRepository.saveAll(personEntityList);

        PersonAddressEntity personAddressEntity1 = new PersonAddressEntity(1, addressRepository.findAll().get(0), personRepository.findAll().get(0));
        PersonAddressEntity2 = new PersonAddressEntity(2, addressRepository.findAll().get(1), personRepository.findAll().get(1));

        List<PersonAddressEntity> tmpList = new ArrayList<>();
        tmpList.add(personAddressEntity1);
        tmpList.add(PersonAddressEntity2);
        personAddressEntityList = Lists.newArrayList(personAddressRepository.saveAll(tmpList));

        personId = personRepository.findAll().get(0).getPersonId();
        addressId = addressRepository.findAll().get(0).getAddressId();
        personAddressId = personAddressRepository.findAll().get(0).getPersonAddressId();

        repositoryCount = personAddressRepository.count();
    }

    @DisplayName("Save a person")
    @Test
    @Order(1)
    void test_save_person_repository() {
        PersonAddressEntity personAddressSaved = personAddressRepository.save(PersonAddressEntity2);
        assertEquals(PersonAddressEntity2.getPersonEntity(), personAddressSaved.getPersonEntity());
        assertEquals(personAddressRepository.count(), repositoryCount + 1);
    }


    @DisplayName("Get a person")
    @Test
    @Order(2)
    void test_get_person_per_id_repository() {
        PersonAddressEntity personAddressSaved = personAddressRepository.findById(personAddressId).get();
        assertEquals(personAddressId, personAddressSaved.getPersonAddressId());
    }

    @DisplayName("Get list of  persons")
    @Test
    @Order(3)
    void test_findAll_person_repository() {
        List<PersonAddressEntity> personAddressEntityFindAll = personAddressRepository.findAll();
        assertEquals(repositoryCount, personAddressEntityFindAll.size());
        assertEquals(personAddressEntityFindAll.get(0).getPersonAddressId(), personAddressEntityList.get(0).getPersonAddressId());
        assertEquals(personAddressEntityFindAll.get(1).getPersonAddressId(), personAddressEntityList.get(1).getPersonAddressId());

    }

    @DisplayName("Update person's field")
    @Test
    @Order(4)
    @Rollback
    void test_update_person_repository() {
        PersonAddressEntity personAddressSaved = personAddressRepository.findById(personAddressId).get();
        personAddressSaved.getPersonEntity().setFirstname("New Name");
        PersonAddressEntity personAddressUpdated = personAddressRepository.save(personAddressSaved);
        assertEquals("New Name", personAddressUpdated.getPersonEntity().getFirstname());
    }

    @DisplayName("Delete a specific persons")
    @Test
    @Order(5)
    @Rollback
    void test_delete_person_repository() {
        PersonAddressEntity personAddressSaved = personAddressRepository.findById(personAddressId).get();
        personAddressRepository.delete(personAddressSaved);

        PersonAddressEntity personAddressEntity2 = null;
        Optional<PersonAddressEntity> optionalPersonAddress2 = personAddressRepository.findById(1);

        if (optionalPersonAddress2.isPresent()) {
            personAddressEntity2 = optionalPersonAddress2.get();
        }
        assertNull(personAddressEntity2);
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
