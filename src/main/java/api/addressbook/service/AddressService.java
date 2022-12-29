package api.addressbook.service;


import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service("AddressService")
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;

    public static String concatAddress(AddressEntity addressEntity) throws MalformedURLException {
        String interpolation = addressEntity.getStreetName() + "+" + addressEntity.getStreetNumber() + ",+" + addressEntity.getZipcode() + "+" + addressEntity.getLocality() + "+" + addressEntity.getCountry();
        URL url = new URL("https://www.google.com/maps/place/" + interpolation);
        logger.info("URL: {}", new URL(url.toString()));
        return url.toString();
    }

    public static String concatAddress(AddressEntity addressEntity, PersonEntity personEntity) {

        String box = " ";
        if (addressEntity.getBoxNumber() != null) {
            box = String.format(" %s ", addressEntity.getBoxNumber());
        }
        String address = addressEntity.getStreetName() + " " + addressEntity.getStreetNumber() + box + System.lineSeparator() + addressEntity.getZipcode() + " " + addressEntity.getLocality() + System.lineSeparator() + addressEntity.getCountry();

        StringBuilder person = new StringBuilder(personEntity.getFirstname());
        if (personEntity.getSecondname() != null) {
            person.append(" ");
            person.append(personEntity.getSecondname());
        }

        if (personEntity.getLastname() != null) {
            person.append(" ");
            person.append(personEntity.getLastname());
        }

        logger.info("Concat Address");
        return person + System.lineSeparator() + address;
    }
}
