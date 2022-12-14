package com.api.addressbook.controller;

import com.api.addressbook.entity.PersonEntity;
import com.api.addressbook.repository.PersonRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/person")
public class PersonController {
    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("/ping")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> getPing() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Just a ping");
        return new ResponseEntity<>("Ping to Person", headers, HttpStatus.ACCEPTED);
    }

    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<List<PersonEntity>> getAllPerson() {
        logger.info("call for all person");
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity getPersonById(@PathVariable("id") int id) {

        if (personRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(personRepository.findById(id));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping("/")
    @PostMapping(value = "/url", produces = "application/json")
    public ResponseEntity<PersonEntity> create(@RequestBody @NonNull PersonEntity body) {
        if (!body.getFirstname().isBlank()) {
            PersonEntity personEntity = personRepository.save(body);
            logger.info("A person was added: {}", personEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(personEntity);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonEntity> update(@PathVariable("id") Integer id, @RequestBody @NonNull PersonEntity body) {
        PersonEntity personEntity = personRepository.findById(id).map(address -> {
            address.setFirstname(body.getFirstname());
            address.setSecondname(body.getSecondname());
            address.setLastname(body.getLastname());
            return personRepository.save(address);
        }).orElseGet(() -> {
            return personRepository.save(body);
        });
        logger.info("A person was updated: {}", personEntity);
        return new ResponseEntity<>(personEntity, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        personRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteAll")
    //todo error if not existing
    public ResponseEntity<Integer> deletePerson() {
        personRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
//todo redirecting when not found?
