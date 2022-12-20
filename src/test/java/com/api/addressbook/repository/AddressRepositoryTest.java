package com.api.addressbook.repository;

import com.api.addressbook.entity.AddressEntity;
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

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit Testing on Address Repository ")
class AddressRepositoryTest {

    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);
    @Autowired
    private AddressRepository addressRepository;

    @DisplayName("Save a address")
    @Test
    @Order(1)
    @Rollback(value = false)
    void test_save_address_repository() {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        AddressEntity addressEntitySaved = addressRepository.save(addressEntity1);
        Assertions.assertThat(addressRepository.count()).isEqualTo(1L);
        assertEquals(addressEntity1.getCountry(), addressEntitySaved.getCountry());
    }

    @DisplayName("Get a address")
    @Test
    @Order(2)
    @Rollback(value = false)
    void test_get_address_per_id_repository() {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        Integer id = addressRepository.save(addressEntity1).getAddressId();
        AddressEntity addressEntitySaved1 = addressRepository.findById(id).get();
        assertEquals(addressEntity1.getAddressId(), addressEntitySaved1.getAddressId());
    }

    @DisplayName("Get list of  addresss")
    @Test
    @Order(3)
    void test_findAll_address_repository() {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        AddressEntity addressEntity2 = new AddressEntity(2, "2", "A", "Test street 2", "2222", "Test City 2", "Test Country 2", true, null);
        addressEntityList.add(addressEntity1);
        addressEntityList.add(addressEntity2);
        addressRepository.saveAll(addressEntityList);
        addressEntityList = addressRepository.findAll();
        assertEquals(2, addressEntityList.size());
        assertEquals(addressEntity1.getAddressId(), addressEntityList.get(0).getAddressId());
        assertEquals(addressEntity2.getAddressId(), addressEntityList.get(1).getAddressId());
    }

    @DisplayName("Update address's field")
    @Test
    @Order(4)
    @Rollback(value = false)
    void test_update_address_repository() {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        addressRepository.save(addressEntity1);
        addressEntity1.setCountry("Dream Land");
        AddressEntity addressEntityUpdated = addressRepository.save(addressEntity1);
        assertEquals("Dream Land", addressEntityUpdated.getCountry());
    }

    @DisplayName("Delete a specific addresss")
    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteaddressTest() {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        AddressEntity addressEntitySaved1 = addressRepository.save(addressEntity1);
        addressRepository.delete(addressEntitySaved1);

        AddressEntity addressEntity2 = null;
        Optional<AddressEntity> optionalAddress2 = addressRepository.findById(1);

        if (optionalAddress2.isPresent()) {
            addressEntity2 = optionalAddress2.get();
        }
        Assertions.assertThat(addressEntity2).isNull();
    }

    @DisplayName("Delete all addresss")
    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteAlladdressTest() {
        AddressEntity addressEntity1 = new AddressEntity(1, "1", null, "Test street", "1111", "Test City", "Test Country", false, null);
        addressRepository.save(addressEntity1);
        addressRepository.deleteAll();
        assertEquals(0, addressRepository.count());
    }
}
