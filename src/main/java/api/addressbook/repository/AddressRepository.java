package api.addressbook.repository;

import api.addressbook.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Override
    List<Address> findAll();
}
