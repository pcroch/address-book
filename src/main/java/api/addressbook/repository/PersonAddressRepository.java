package api.addressbook.repository;

import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;


public interface PersonAddressRepository extends CrudRepository<PersonAddressEntity, Integer> {

    PersonAddressEntity findByPersonIdAndAddressId(Integer personId, Integer addressId);
}
