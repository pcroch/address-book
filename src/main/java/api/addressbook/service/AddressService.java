package api.addressbook.service;

import api.addressbook.entity.AddressEntity;
import api.addressbook.model.Person;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service("AddressService")
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;

    public static String concatAddress(AddressEntity address) throws MalformedURLException {
        String interpolation = address.getStreetName() + "+" + address.getStreetNumber() + ",+" + address.getZipcode() + "+" + address.getLocality() + "+" + address.getCountry();
        URL url = new URL("https://www.google.com/maps/place/" + interpolation);
        log.info("URL: {}", new URL(url.toString()));
        return url.toString();
    }

    public static String concatAddress(AddressEntity address, Person person) {

        String box = " ";
        if (address.getBoxNumber() != null) {
            box = String.format(" %s ", address.getBoxNumber());
        }
        String tmpAddress = address.getStreetName() + " " + address.getStreetNumber() + box + System.lineSeparator() + address.getZipcode() + " " + address.getLocality() + System.lineSeparator() + address.getCountry();

        StringBuilder tmpPerson = new StringBuilder(person.getFirstname());
        if (person.getSecondname() != null) {
            tmpPerson.append(" ");
            tmpPerson.append(person.getSecondname());
        }

        if (person.getLastname() != null) {
            tmpPerson.append(" ");
            tmpPerson.append(person.getLastname());
        }

        log.info("Concat Address");
        return tmpPerson + System.lineSeparator() + tmpAddress;
    }
}
