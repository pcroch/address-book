package api.addressbook.repository;

import api.addressbook.entity.QRCodeEntity;
import api.addressbook.model.QRCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QRCodeRepository extends CrudRepository<QRCodeEntity, Integer> {
    QRCodeEntity findByQrCodeName(String name);

    boolean existsByQrCodeName(String name);
}

