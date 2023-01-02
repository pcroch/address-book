//package api.addressbook.repository;
//
//import api.addressbook.entity.AddressEntity;
//import api.addressbook.model.Address;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DisplayName("Unit Testing on Address Repository ")
//class AddressRepositoryTest {
//
//    private Address address1;
//    private Address address2;
//    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @BeforeEach
//    public void setUp() {
//        address1 = Address.builder()
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
//        address2 = Address.builder()
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
//    }
//
//    @DisplayName("Save a address")
//    @Test
//    @Order(1)
//    @Rollback(value = false)
//    void test_save_address_repository() { // making abstract class for the testing et repo
//        AddressEntity addressSaved = addressRepository.save(address1);
//        Assertions.assertThat(addressRepository.count()).isEqualTo(1L);
//        assertEquals(address1.getCountry(), addressSaved.getCountry());
//    }
//
//    @DisplayName("Get a address")
//    @Test
//    @Order(2)
//    @Rollback(value = false)
//    void test_get_address_per_id_repository() {
//        Integer id = addressRepository.save(address1).getAddressId();
//        Address addressSaved1 = addressRepository.findById(id).get();
//        assertEquals(address1.getAddressId(), addressSaved1.getAddressId());
//    }
//
//    @DisplayName("Get list of  addresss")
//    @Test
//    @Order(3)
//    void test_findAll_address_repository() {
//        List<AddressEntity> addressList = new ArrayList<>();
//        addressList.add(address1);
//        addressList.add(address2);
//        addressRepository.saveAll(addressList);
//        addressList = addressRepository.findAll();
//        assertEquals(2, addressList.size());
//        assertEquals(address1.getAddressId(), addressList.get(0).getAddressId());
//        assertEquals(address2.getAddressId(), addressList.get(1).getAddressId());
//    }
//
//    @DisplayName("Update address's field")
//    @Test
//    @Order(4)
//    @Rollback(value = false)
//    void test_update_address_repository() {
//        addressRepository.save(address1);
//        address1.setCountry("Dream Land");
//        Address addressUpdated = addressRepository.save(address1);
//        assertEquals("Dream Land", addressUpdated.getCountry());
//    }
//
//    @DisplayName("Delete a specific addresss")
//    @Test
//    @Order(5)
//    @Rollback(value = false)
//    void deleteaddressTest() {
//        Address addressSaved1 = addressRepository.save(address1);
//        addressRepository.delete(addressSaved1);
//
//        Address address2 = null;
//        Optional<Address> optionalAddress2 = addressRepository.findById(1);
//
//        if (optionalAddress2.isPresent()) {
//            address2 = optionalAddress2.get();
//        }
//        Assertions.assertThat(address2).isNull();
//    }
//
//    @DisplayName("Delete all addresss")
//    @Test
//    @Order(5)
//    @Rollback(value = false)
//    void deleteAlladdressTest() {
//        addressRepository.save(address1);
//        addressRepository.deleteAll();
//        assertEquals(0, addressRepository.count());
//    }
//}
