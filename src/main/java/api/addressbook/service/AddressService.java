package api.addressbook.service;


import api.addressbook.entity.AddressEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service("AddressService")
public class AddressService {

    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    @Autowired
    private  AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;

    public static String concatAddress(Optional<AddressEntity> addressEntity) throws MalformedURLException {
        String interpolation = addressEntity.get().getStreetName()+"+"+  addressEntity.get().getStreetNumber()+",+"+addressEntity.get().getZipcode()+"+"+ addressEntity.get().getLocality();
        URL url = new URL("https://www.google.com/maps/place/"+interpolation);
         logger.info("URL: {}", new URL(url.toString()));
        return url.toString();



    }
}
