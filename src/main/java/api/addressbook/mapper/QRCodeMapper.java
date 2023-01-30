package api.addressbook.mapper;

import api.addressbook.entity.QRCodeEntity;
import api.addressbook.model.QRCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QRCodeMapper {

    @Mapping(source = "qrCodeId", target = "qrCodeId")
    @Mapping(source = "qrCodeName", target = "qrCodeName")
    @Mapping(source = "qrCodeImage", target = "qrCodeImage")
    @Mapping(source = "personAddressEntity", target = "personAddress")
    QRCode toDomain(QRCodeEntity e);
    QRCodeEntity EntityToModel(QRCode e);
}

