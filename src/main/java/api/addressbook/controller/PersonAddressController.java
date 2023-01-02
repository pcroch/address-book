package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.model.Address;
import api.addressbook.model.Person;
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
public class PersonAddressController extends AbstractController{

    @RequestMapping("/concat/{addressId}/{personId}")
    @GetMapping(value = "/url")
    public ResponseEntity<String> concatPersonAddress(@PathVariable("addressId") int addressId, @PathVariable("personId") int personId) {
        Optional<AddressEntity> address = addressRepository.findById(addressId);
        Optional<Person> person= personRepository.findById(personId).map(personMapper::toDomain);

        if (person.isEmpty() || address.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.CONTENT_DISPOSITION).body(concatAddress(address.get(), person.get()));
    }
}

