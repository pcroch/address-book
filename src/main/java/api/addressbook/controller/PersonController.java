package api.addressbook.controller;

import api.addressbook.entity.PersonEntity;
import api.addressbook.repository.PersonRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/person")
public class PersonController extends AbstractController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<List<PersonEntity>> getAllPerson() {
        logger.info("call for all person {}", personRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(personRepository.findAll());
    }

    @RequestMapping("/{id}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Optional<PersonEntity>> getPersonById(@PathVariable("id") int id) {

        if (personRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(personRepository.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping("/")
    @PostMapping(value = "/url", produces = "application/json")
    public ResponseEntity<PersonEntity> createPerson(@RequestBody @NonNull PersonEntity body) {
        if (!body.getFirstname().isBlank()) {
            addressRepository.saveAll(body.getAddress());
            PersonEntity personEntity = personRepository.save(body);

            logger.info("A person was added: {}", personEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(personEntity);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonEntity> updatePerson(@PathVariable("id") Integer id, @RequestBody @NonNull PersonEntity body) {
        PersonEntity personEntity = personRepository.findById(id).map(address -> {
            address.setFirstname(body.getFirstname());
            address.setSecondname(body.getSecondname());
            address.setLastname(body.getLastname());
            return personRepository.save(address);
        }).orElseGet(() -> {
            return personRepository.save(body);
        });
        logger.info("A person was updated: {}", personEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(personEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteAllPerson(@PathVariable("id") Integer id) {
        if (personRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Integer> deletePersonPerId() {
        personRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

