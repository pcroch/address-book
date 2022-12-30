package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.model.Address;
import api.addressbook.repository.AddressRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/address")
public class AddressController extends AbstractController {

//    private final AddressRepository addressRepository;
//
//    public AddressController(AddressRepository addressRepository) {
//        this.addressRepository = addressRepository;
//    }
//

    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Iterable<Address>> getAllPerson() {
        logger.info("call for all address {}", addressRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findAll());
    }

    @RequestMapping("/{id}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Optional<Address>> getPersonById(@PathVariable("id") int id) {
        if (addressRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping("/")
    @PostMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Address> createAddress(@RequestBody @NonNull Address body) {
        if (!body.getStreetNumber().isBlank()) {
            Address address = addressRepository.save(body);
            personRepository.saveAll(body.getPerson());
            logger.info("An address was added: {}", address);
            return ResponseEntity.status(HttpStatus.CREATED).body(address);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable("id") Integer id, @RequestBody @NonNull Address body) {
        Address address = addressRepository.findById(id).map(ad -> {
            ad.setCountry(body.getCountry());
            ad.setPrivate(body.isPrivate());
            ad.setLocality(body.getLocality());
            ad.setPerson(body.getPerson());
            ad.setStreetName(body.getStreetName());
            ad.setBoxNumber(body.getBoxNumber());
            ad.setStreetNumber(body.getStreetNumber());
            return addressRepository.save(ad);
        }).orElseGet(() -> {
            return addressRepository.save(body);
        });
        logger.info("An address has been updated: {}", address);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(address);
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
