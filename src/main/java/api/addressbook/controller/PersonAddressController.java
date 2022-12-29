package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.entity.PersonEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static api.addressbook.service.AddressService.concatAddress;

@Controller
@RequestMapping("/person-address")
public class PersonAddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/ping")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> getPingPersonAddress() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>("You have reached on PersonAddress Endpoints", headers, HttpStatus.ACCEPTED);
    }

    @RequestMapping("/concat/{addressId}/{personId}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> concatPersonAddress(@PathVariable("addressId") int addressId, @PathVariable("personId") int personId) {
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        Optional<PersonEntity> personEntity = personRepository.findById(personId);

        if (personEntity.isEmpty() || addressEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.CONTENT_DISPOSITION).body(concatAddress(addressEntity.get(), personEntity.get()));
    }
}

