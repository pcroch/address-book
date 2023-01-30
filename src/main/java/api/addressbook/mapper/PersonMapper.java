package api.addressbook.mapper;

import api.addressbook.entity.PersonEntity;
import api.addressbook.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "personId", target = "personId")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "secondname", target = "secondname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "personAddressEntity", target = "personAddressEntity")
    Person toDomain(PersonEntity e);
    PersonEntity toMap(Person e);
}

