package api.addressbook.repository;


import api.addressbook.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    List<AddressEntity> findAll();
}