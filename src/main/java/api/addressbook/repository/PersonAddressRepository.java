package api.addressbook.repository;

import api.addressbook.entity.PersonAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonAddressRepository  extends CrudRepository<PersonAddressEntity, Integer> {

    @Override
    List<PersonAddressEntity> findAll();

//    PersonAddressEntity findByPersonIdAndAddressId(AddressEntity addressEntity, PersonEntity personEntity);
}
