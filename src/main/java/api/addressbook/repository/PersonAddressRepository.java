package api.addressbook.repository;

import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.entity.PersonEntity;
import api.addressbook.model.PersonAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonAddressRepository extends CrudRepository<PersonAddressEntity, Integer> {

    List<PersonAddressEntity> findAll();

    PersonAddressEntity findByPersonIdAndAddressId(Integer personId, Integer addressId);
}
