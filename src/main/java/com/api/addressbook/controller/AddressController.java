package com.api.addressbook.controller;

import com.api.addressbook.entity.AddressEntity;
import com.api.addressbook.entity.PersonEntity;
import com.api.addressbook.repository.AddressRepository;
import com.api.addressbook.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class AddressController {

    public static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping("")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<Iterable<AddressEntity>> getAllPerson() {
        logger.info("call for all address {}", addressRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(addressRepository.findAll());
    }

}
