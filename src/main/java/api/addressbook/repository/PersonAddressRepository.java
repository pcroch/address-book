package api.addressbook.repository;

import api.addressbook.model.PersonAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonAddressRepository extends CrudRepository<PersonAddress, Integer> {

    PersonAddress findByPersonIdAndAddressId(Integer personId, Integer addressId);
}
