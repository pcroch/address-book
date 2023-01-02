package api.addressbook.controller;

import api.addressbook.entity.PersonEntity;
import api.addressbook.model.Person;
import api.addressbook.repository.PersonRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/person")
public class PersonController extends AbstractController {


    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<List<Person>> getAllPerson() {
        logger.info("call for all person {}", personRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(personRepository.findAll().stream().map(personMapper::toDomain).collect(Collectors.toList()));
    }

    @RequestMapping("/{id}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable("id") int id) {

        if (personRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(personRepository.findById(id).map(personMapper::toDomain));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping("/")
    @PostMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Person> createPerson(@RequestBody @NonNull PersonEntity body) {
        if (!body.getFirstname().isBlank()) {
//            addressRepository.saveAll(body.getAddress());
            Person person = personMapper.toDomain(personRepository.save(body));
            logger.info("A person was added: {}", person);
            return ResponseEntity.status(HttpStatus.CREATED).body(person);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Integer id, @RequestBody @NonNull PersonEntity body) {
        Person person = personRepository.findById(id).map(address -> {
            address.setFirstname(body.getFirstname());
            address.setSecondname(body.getSecondname());
            address.setLastname(body.getLastname());
            return personRepository.save(address);
        }).map(personMapper::toDomain).orElseGet(() -> {
            return personMapper.toDomain(personRepository.save(body));
        });
        logger.info("A person was updated: {}", person);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(person);
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

