package api.addressbook.repository;

import api.addressbook.entity.PersonEntity;
import api.addressbook.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Integer> {

    List<PersonEntity> findAll();
}
