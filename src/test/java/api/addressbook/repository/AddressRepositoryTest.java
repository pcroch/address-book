package api.addressbook.repository;

import api.addressbook.entity.AddressEntity;
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
@Disabled
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Address Repository ")
class AddressRepositoryTest  extends AbstractRepository {

    private AddressEntity address1, address2, address3;
    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);
    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        addressRepository.deleteAll();

        address1 = new AddressEntity(1, "1", "4B", "rue du trone", "1000", "Bruxelles", "Belgium", false, null);
        address2 = new AddressEntity(2, "12", null, "rue du roi", "5852", "Namur", "Belgium", false, null);
        address3 = new AddressEntity(3, "13", "5", "rue du lac", "7200", "Anvers", "Belgium", false, null);

        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(address1);
        addressEntityList.add(address2);
        addressRepository.saveAll(addressEntityList);
        repositoryCount = addressRepository.count();
    }

    @DisplayName("Save a address")
    @Test
    @Order(1)
    @Rollback(value = false)
    void test_save_address_repository() {
        AddressEntity addressSaved = addressRepository.save(address1);
        Assertions.assertThat(addressRepository.count()).isEqualTo(repositoryCount + 1);
        assertEquals(address3.getCountry(), addressSaved.getCountry());
    }

    @DisplayName("Get a address")
    @Test
    @Order(2)
    @Rollback(value = false)
    void test_get_address_per_id_repository() {
        Integer id = addressRepository.save(address3).getAddressId();
        AddressEntity addressSaved1 = addressRepository.findById(id).get();
        assertEquals(id, addressSaved1.getAddressId());
    }

    @DisplayName("Get list of addresses")
    @Test
    @Order(3)
    void test_findAll_address_repository() {
        List<AddressEntity> addressList;
        addressList = addressRepository.findAll();
        assertEquals(repositoryCount, addressList.size());
        assertEquals(address1.getCountry(), addressList.get(0).getCountry());
        assertEquals(address2.getCountry(), addressList.get(1).getCountry());
    }

    @DisplayName("Update address's field")
    @Test
    @Order(4)
    @Rollback(value = false)
    void test_update_address_repository() {
        address1.setCountry("Dream Land");
        AddressEntity addressUpdated = addressRepository.save(address1);
        assertEquals("Dream Land", addressUpdated.getCountry());
    }

    @DisplayName("Delete a specific addresss")
    @Test
    @Order(5)
    @Rollback(value = false)
    void test_delete_address_repository() {
       Integer id = addressRepository.save(address3).getAddressId();
        AddressEntity addressSaved1 = addressRepository.findById(id).get();
        addressRepository.delete(addressSaved1);

        AddressEntity address2 = null;
        Optional<AddressEntity> optionalAddress2 = addressRepository.findById(1);

        if (optionalAddress2.isPresent()) {
            address2 = optionalAddress2.get();
        }
        Assertions.assertThat(address2).isNull();
    }

    @DisplayName("Delete all addresss")
    @Test
    @Order(5)
    @Rollback(value = false)
    void test_delete_all_address_repository() {
        addressRepository.deleteAll();
        assertEquals(0, addressRepository.count());
    }
}
