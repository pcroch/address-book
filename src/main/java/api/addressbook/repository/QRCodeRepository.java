package api.addressbook.repository;



import api.addressbook.entity.QRCodeEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QRCodeRepository extends CrudRepository<QRCodeEntity, Integer> {
//    QRCodeEntity findByPersonIdAndAddressId(Integer personId, Integer addressId);
}
