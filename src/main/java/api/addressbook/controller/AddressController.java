package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/address")
public class AddressController extends AbstractController{

    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Iterable<AddressEntity>> getAllPerson() {
        logger.info("call for all address {}", addressRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findAll());
    }

    @RequestMapping("/{id}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Optional<AddressEntity>> getPersonById(@PathVariable("id") int id) {

        if (addressRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping("/")
    @PostMapping(value = "/url", produces = "application/json")
    public ResponseEntity<AddressEntity> createAddress(@RequestBody @NonNull AddressEntity body) {
        if (!body.getStreetNumber().isBlank()) {
            AddressEntity addressEntity = addressRepository.save(body);
            personRepository.saveAll(body.getPerson());
            logger.info("An address was added: {}", addressEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(addressEntity);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressEntity> updateAddress(@PathVariable("id") Integer id, @RequestBody @NonNull AddressEntity body) {
        AddressEntity addressEntity = addressRepository.findById(id).map(address -> {
            address.setCountry(body.getCountry());
            address.setPrivate(body.isPrivate());
            address.setLocality(body.getLocality());
            address.setPerson(body.getPerson());
            address.setStreetName(body.getStreetName());
            address.setBoxNumber(body.getBoxNumber());
            address.setStreetNumber(body.getStreetNumber());
            return addressRepository.save(address);
        }).orElseGet(() -> {
            return addressRepository.save(body);
        });
        logger.info("An address has been updated: {}", addressEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(addressEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteAllAddress(@PathVariable("id") Integer id) {
        if (addressRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Integer> deleteAddressPerId() {
        addressRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
