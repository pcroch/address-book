package api.addressbook.controller;

import api.addressbook.entity.AddressEntity;
import api.addressbook.mapper.AddressMapper;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.model.Address;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/address")
public class AddressController extends AbstractController {
    // should it be final?

    private final AddressRepository addressRepository;

    private final PersonRepository personRepository;

 private final AddressMapper addressMapper;
    PersonMapper personMapper;

    @Autowired
    public AddressController(AddressRepository addressRepository, PersonRepository personRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
        this.addressMapper = addressMapper;
    }

    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<List<Address>> getAllPerson() {
        logger.info("call for all address {}", addressRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findAll().stream().map(addressMapper::toDomain).collect(Collectors.toList()));
    }

    @RequestMapping("/{id}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Optional<Address>> getPersonById(@PathVariable("id") int id) {
        if (addressRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findById(id).map(addressMapper::toDomain));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping("/")
    @PostMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Address> createAddress(@RequestBody @NonNull AddressEntity body) {
        if (!body.getStreetNumber().isBlank()) {
            Address address = addressMapper.toDomain(addressRepository.save(body));
            personRepository.saveAll(body.getPerson());
            logger.info("An address was added: {}", address);
            return ResponseEntity.status(HttpStatus.CREATED).body(address);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable("id") Integer id, @RequestBody @NonNull AddressEntity body) {
        Address address = addressRepository.findById(id).map(ad -> {
            ad.setCountry(body.getCountry());
            ad.setIsPrivate(body.getIsPrivate());
            ad.setLocality(body.getLocality());
            ad.setPerson(body.getPerson());
            ad.setStreetName(body.getStreetName());
            ad.setBoxNumber(body.getBoxNumber());
            ad.setStreetNumber(body.getStreetNumber());
            return addressRepository.save(ad);
        }).map(addressMapper::toDomain).orElseGet(() -> {
            return addressMapper.toDomain(addressRepository.save(body));
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
