package api.addressbook.repository;

import api.addressbook.model.QRCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QRCodeRepository extends CrudRepository<QRCode, Integer> {
    QRCode findByQrCodeName(String name);

    boolean existsByQrCodeName(String name);
}

