package api.addressbook.mapper;

import api.addressbook.entity.PersonAddressEntity;
import api.addressbook.model.PersonAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,  componentModel = "spring")
public interface PersonAddressMapper {

    @Mapping(source = "personAddressId", target = "personAddressId")
    @Mapping(source = "addressId", target = "addressId")
    @Mapping(source = "personId", target = "personId")
    PersonAddress toDomain(PersonAddressEntity e);
}

