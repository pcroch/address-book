package api.addressbook.mapper;

import api.addressbook.entity.QRCodeEntity;
import api.addressbook.model.QRCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QRCodeMapper {

    @Mapping(source = "personAddressId", target = "personAddressId")
    @Mapping(source = "qrCodeName", target = "qrCodeName")
    @Mapping(source = "qrCodeImage", target = "qrCodeImage")
    @Mapping(source = "personAddress", target = "personAddress")
    QRCode toDomain(QRCodeEntity e);
    QRCodeEntity EntityToModel(QRCode e);
}

