package api.addressbook.controller;


import api.addressbook.mapper.AddressMapper;
import api.addressbook.mapper.PersonAddressMapper;
import api.addressbook.mapper.PersonMapper;
import api.addressbook.mapper.QRCodeMapper;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.PersonAddressRepository;
import api.addressbook.repository.PersonRepository;
import api.addressbook.repository.QRCodeRepository;
import api.addressbook.service.QRCodeGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class AbstractController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    protected PersonRepository personRepository;



    @Autowired
    protected PersonAddressRepository personAddressRepository;

    @Autowired
    protected QRCodeRepository qrcodeRepository;

    @Autowired
    protected QRCodeGeneratorService qrcodeGeneratorService;


    protected PersonMapper personMapper;


    protected AddressMapper addressMapper;


    protected PersonAddressMapper personAddressMapper;


    protected QRCodeMapper qrcodeMapper;

    @RequestMapping("/ping")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> ping() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", this.getClass().getSimpleName() + " endpoints has been pinged");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(this.getClass().getSimpleName() + " Ping");
    }
}
