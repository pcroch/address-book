package api.addressbook.mapper;

import api.addressbook.entity.AddressEntity;
import api.addressbook.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "addressId", target = "addressId")
    @Mapping(source = "streetNumber", target = "streetNumber")
    @Mapping(source = "boxNumber", target = "boxNumber")
    @Mapping(source = "streetName", target = "streetName")
    @Mapping(source = "zipcode", target = "zipcode")
    @Mapping(source = "locality", target = "locality")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "isPrivate", target = "isPrivate")
    @Mapping(source = "person", target = "person")
    Address toDomain(AddressEntity e);
    AddressEntity toMap(Address e);
}

