package api.addressbook.controller;


import api.addressbook.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class AbstractController {

    public final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/ping")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> ping() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", this.getClass().getSimpleName() +  " endpoints has been pinged");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(this.getClass().getSimpleName() + " Ping");
    }
}
