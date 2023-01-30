package api.addressbook.repository;

import api.addressbook.entity.QRCodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QRCodeRepository extends CrudRepository<QRCodeEntity, Integer> {
    QRCodeEntity findByQrCodeName(String name);

    boolean existsByQrCodeName(String name);

    @Override
    List<QRCodeEntity> findAll();
}

